package com.sinoiift.service.impl;

import com.sinoiift.domain.App;
import com.sinoiift.service.ImpSqlFileService;
import com.sinoiift.utils.DBUtils;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xg on 2018/1/18.
 */
@Service
public class CSIIImpSqlFileServiceImpl implements ImpSqlFileService {
    private static Logger log = LoggerFactory.getLogger(CSIIImpSqlFileServiceImpl.class);
    private static final String regEx_clob = ">>>>>>>>\\/*?[\\s\\S]*?\\/>>>>>>>>"; // 定义大字段格式

    @Override
    public void executeSql(List<String> lis, String workspace, App app) throws Exception {
        if (!workspace.endsWith("/")) {
            workspace += "/";
        }
        String backUrl = workspace + app.getApp() +"/backup/";
        DataSource dataSource = DBUtils.createDataSource(app);
        try {
            Connection conn = dataSource.getConnection();
            try {
                readFile(lis, conn, 100, backUrl);
            } finally {
                conn.close();
            }
        } finally {
            dataSource.close();
        }
    }

    /**
     * 读取导入文件
     *
     * commitInt 多少条sql执行一次commit
     *
     */
    public void readFile(List<String> lis, Connection con, int commitInt, String backUrl) throws Exception {
        BufferedReader re = null;
        StringBuffer sb = new StringBuffer();
        try {
            for (String fileName : lis) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(fileName), "GBK");
                re = new BufferedReader(isr);
                con.setAutoCommit(false);
                String tempString = null;
                Map<String, Integer> mapInt = new HashMap<String, Integer>();
                mapInt.put("comInt", 0);
                while ((tempString = re.readLine()) != null) {
                    //执行
                    makeUpSql(tempString.trim(), sb, con, mapInt, backUrl);
                    if (mapInt.get("comInt") == commitInt) {
                        con.commit();
                        mapInt.put("comInt", 0);
                        log.info("事物提交");

                    }

                }
                if (sb.length() > 0) {
                    makeUpSql("1qaz2wsx3edc", sb, con, mapInt, backUrl);
                }
                if (mapInt.get("comInt") > 0) {
                    con.commit();
                    mapInt.put("comInt", 0);
                    log.info("执行完成");
                }
                log.info(fileName + "文件执行成功");
            }

        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            log.error("数据回滚");
            throw e;

        } finally {
            if (re != null) {
                try {
                    re.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 拼凑数据
     */
    public void makeUpSql(String tempString, StringBuffer sb, Connection con, Map<String, Integer> mapInt, String backUrl) throws Exception {

        if (tempString == null || "".equals(tempString)) {
            return;
            //throw new Exception("tempString数据为空");
        } else {
            //((tempString.indexOf("delete")>=0||tempString.indexOf("insert")>=0)&&tempString.length()>0)||tempString.trim().lastIndexOf(";")==tempString.trim().length()-1
            if (((tempString.indexOf("delete") >= 0 || tempString.indexOf("insert") >= 0) && tempString.length() > 0) || "1qaz2wsx3edc".equals(tempString)) {


                if (sb.toString().indexOf(";") != -1) {
                    //执行sql

                    //	Statement statement=(Statement) con.createStatement();
                    //执行
                    Map<String, Object> zxSql = changSql(sb.toString());
                    if (zxSql == null) {
                        log.error("执行错误！！");
                    } else {
                        if (zxSql.get("sql").toString().indexOf("delete") >= 0 || zxSql.get("sql").toString().indexOf("insert") >= 0) {
                            PreparedStatement pState = con.prepareStatement(zxSql.get("sql").toString());
                            if (zxSql.size() == 2) {
                                List<String> mz = (List<String>) zxSql.get("value");
                                for (int ii = 0; ii < mz.size(); ii++) {
                                    StringReader reader = new StringReader(mz.get(ii).toString());
                                    pState.setCharacterStream(ii + 1, reader, mz.get(ii).length());
                                }
                            }
                            if (zxSql.get("sql").toString().indexOf("delete") >= 0) {
                                String delStr = zxSql.get("sql").toString();
                                delStr = delStr.substring(delStr.indexOf("from") + 4).trim();
                                String tableName = delStr.substring(0, delStr.indexOf(" "));
                                String whereStr = delStr.substring(delStr.indexOf(" ")).trim();
                                backups(whereStr, con, tableName, backUrl);
                            }
                            pState.executeUpdate();
                            pState.close();
                            int coInt = mapInt.get("comInt");
                            coInt += 1;
                            mapInt.put("comInt", coInt);
                            sb.setLength(0);
                        } else {
                            sb.setLength(0);
                        }
                    }
                }

                if (tempString.trim().indexOf("--") != 0 && tempString.trim().indexOf("set") != 0 && tempString.trim().indexOf("commit") != 0 && tempString.trim().indexOf("quit") != 0) {
                    sb.append(tempString);
                }

            } else {
                //拼凑sql
                if (sb.length() > 0) {
                    if (tempString.trim().indexOf("--") != 0 && tempString.trim().indexOf("set") != 0 && tempString.trim().indexOf("commit") != 0 && tempString.trim().indexOf("quit") != 0) {
                        sb.append(tempString);
                    }
                }
            }
        }
    }

    /**
     * 除去sql中“”符号
     */
    public Map<String, Object> changSql(String sbs) {
        Map<String, Object> reMap = new HashMap<String, Object>();
        if (sbs.length() > 0 && sbs != null) {
            int len = sbs.indexOf("values");
            if (len >= 0 && sbs.trim().lastIndexOf(";") == sbs.trim().length() - 1) {
                String wsql = sbs.substring(0, len + 6).replace("\"", "");
                String vsbs = sbs.substring(len + 6, sbs.length());
                //判定[1,7]情况
                List<String> list = new ArrayList<String>();
                Map<String, String> map = new HashMap<String, String>();
                Matcher m = Pattern.compile(regEx_clob).matcher(vsbs);
                while (m.find()) {
                    String ssstr = m.group();
                    vsbs = vsbs.replace(ssstr, "?----" + m.start());
//				   list.add(ssstr.replace(">>>>>>>>/", "").replace("/>>>>>>>>", ""));
                    map.put(m.start() + "", ssstr);
                }
                int zuoLen = vsbs.indexOf("(");
                int youLen1 = vsbs.lastIndexOf(");");
                String[] valSbs = vsbs.substring(zuoLen + 1, youLen1).replace(",", ",,").replace("},,", "},").replace("\'\',,", "\'\',").split(",,");
                for (int i = 0; i < valSbs.length; i++) {
                    if (valSbs[i].indexOf("?----") > -1) {
                        String ss = valSbs[i].split("----")[1];
                        list.add(map.get(valSbs[i].split("----")[1]).replace(">>>>>>>>/'", "").replace("'/>>>>>>>>", ""));
                        valSbs[i] = "?";
                    } else {
                        if (valSbs[i].length() > 4000) {
                            list.add(valSbs[i].trim().substring(1, valSbs[i].trim().length() - 1).replace("\'\'", "\'"));
                            valSbs[i] = "?";
                        }
                    }
                }
                StringBuffer strb = new StringBuffer();
                for (String valSb : valSbs) {
                    strb.append(valSb + ",");
                }
                sbs = wsql + " (" + strb.substring(0, strb.length() - 1).toString() + " )";
//			   sbs=wsql+vsbs;
                reMap.put("sql", sbs);
                if (list.size() > 0) {
                    reMap.put("value", list);
                }
                log.info("newSql--->" + sbs);
            } else {
                int inLen = sbs.lastIndexOf(";");
                sbs = sbs.substring(0, inLen);
                int valLen = sbs.indexOf("values");
                if (valLen > -1) {
                    sbs = sbs.substring(0, valLen).replace("\"", "") + sbs.substring(valLen, sbs.length());
                } else {
                    sbs = sbs.replace("\"", "");
                }
                log.info("newSql--->" + sbs);
                reMap.put("sql", sbs);
            }
            return reMap;
        } else {
            log.error("除去sql中“”符号错误");
            return null;
        }
    }

    /**
     * 根据删除条件备份数据库数据
     */
    public List<String> backups(String condition, Connection con, String tablename, String backUrl) {
        try {
            List sqls = new ArrayList();
            Statement sta = con.createStatement();
            String sql = "select * from " + tablename + " " + condition;
            ResultSet rs = sta.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            int typevalue = 0;
            int[] intvalue = new int[10];
            int j = 0;

            String ColumnName = null;
            String AllColumnName = new String();
            String ColumTypeName = new String();
            for (int i = 1; i <= col; i++) {
                ColumnName = rsmd.getColumnName(i);
                AllColumnName = AllColumnName + ColumnName + ",";
                typevalue = rsmd.getColumnType(i);
            }
            sqls.add("--表名:" + tablename + "条件:" + condition);
            String delsql = null;
            //如果是MENU_CONTROL表，只生成删除语句，不生成插入语句
            if (tablename.equalsIgnoreCase("MENU_CONTROL")) {
                if (condition.contains("BSCODE")
                        || condition.contains("bscode")
                        || condition.contains("BRC") || condition
                        .contains("brc") || condition
                        .contains("TELLERCODE") || condition
                        .contains("tellercode")) {
                    delsql = "delete FROM " + tablename + " " + condition;
                    sqls.add(delsql);
                    return sqls;
                } else {
                    return sqls;
                }
            } else {
                delsql = "delete FROM " + tablename + " " + condition;
                sqls.add(delsql);
            }
            while (rs.next()) {
                String valuetemp = "";
                for (int i = 1; i <= col; i++) {
                    ColumnName = rsmd.getColumnName(i);
                    typevalue = rsmd.getColumnType(i);
                    String name = rsmd.getColumnName(i);
                    switch (typevalue) {
                        case 1:
                            String charValue = rs.getString(ColumnName);
                            if (charValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " '"
                                        + charValue.replaceAll("'", "''") + "', ";
                            break;
                        case 12:
                            String varcharValue = rs.getString(ColumnName);
                            if (varcharValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " '"
                                        + varcharValue.replaceAll("'", "''")
                                        + "', ";
                            break;
                        case 93:
                            Timestamp timeValue = rs.getTimestamp(ColumnName);
                            if (timeValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " TIMESTAMP('" + timeValue
                                        + "'), ";
                            break;
                        case 91:
                            String dateStr = rs.getString(ColumnName);
                            if (dateStr == null)
                                valuetemp = valuetemp + " null, ";
                            else {
                                if (dateStr.length() > 19) {
                                    dateStr = dateStr.substring(0, 19);
                                }
                                valuetemp = valuetemp + "to_date( '"
                                        + dateStr + "','yyyy-MM-dd HH24:mi:ss'), ";
                            }
                            break;
                        case -5:
                            String bigValue = rs.getString(ColumnName);
                            if (bigValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " " + bigValue + ", ";
                            break;
                        case 4:
                            Integer intValue = Integer.valueOf(rs
                                    .getInt(ColumnName));
                            //2013-07-29
                            //由于cdjg表的order字段用于菜单的排序
                            //且rs.getInt(ColumnName)当取到的值是null时默认返回0，从而影响了排序的结果
                            //因此这里针对cdjg的order字段做了特殊的返回处理
                            if (tablename.equalsIgnoreCase("cdjg") && ColumnName.equalsIgnoreCase("order")) {
                                if (intValue == 0) {
                                    intValue = null;
                                }
                            }
                            if (intValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " " + intValue + ", ";
                            break;
                        case 5:
                            String smallintValue = rs.getString(ColumnName);
                            if (smallintValue == null)
                                valuetemp = valuetemp + " null, ";
                            else
                                valuetemp = valuetemp + " " + smallintValue + ", ";
                            break;
                        case 2005:
                            String clobValue = rs.getString(ColumnName);
                            if (clobValue == null) {
                                valuetemp = valuetemp + " null, ";
                            } else {
                                valuetemp = valuetemp + " >>>>>>>>/'" + clobValue + "'/>>>>>>>>, ";
                            }
                            break;
                        default:
                            String otherValue = rs.getString(ColumnName);
                            if (otherValue == null)
                                valuetemp = valuetemp + " null, ";
                            else {
                                valuetemp = valuetemp + " '"
                                        + otherValue.replaceAll("'", "''") + "', ";
                            }
                    }

                }

                String insertsql = "insert into "
                        + tablename
                        + "("
                        + AllColumnName
                        .substring(0, AllColumnName.length() - 1)
                        + ")values("
                        + valuetemp.substring(0, valuetemp.length() - 2) + ")";
                sqls.add(insertsql);
            }
            rs.close();
            sta.close();
            writeFile(sqls, backUrl);
            return sqls;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将备份sql写入指定位置
     */
    public void writeFile(List li, String exportFile) {
        Date date = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
        String filename = sd.format(date).replace(" ", "").replace(":", "") + ".sql";
        String str[] = sd.format(date).split(" ");
        String newdate = sd.format(date).replace(" ", "").replace(":", "");
        if (exportFile.endsWith("/")) {
            exportFile += str[0] + "/";
        } else {
            exportFile += "/" + str[0] + "/";
        }
        File fil = new File(exportFile);
        if (!fil.exists()) {
            fil.mkdirs();
        }
        FileWriter fw = null;
        try {
            File ff = new File(exportFile + filename);
            ff.createNewFile();
            StringBuffer content = new StringBuffer();
            for (int i = 0; i < li.size(); i++) {
                String contentStr = (String) li.get(i);
                content.append(contentStr + ";\n");
            }
            fw = new FileWriter(ff);
            fw.write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.flush();
                    fw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            fw = null;
        }

    }
}
