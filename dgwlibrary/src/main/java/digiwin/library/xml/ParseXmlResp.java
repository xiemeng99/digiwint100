package digiwin.library.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import com.thoughtworks.xstream.io.xml.XppDriver;

import java.util.ArrayList;
import java.util.List;

import digiwin.library.utils.LogUtils;

/**
 * @des      xml报文解析
 * @author  xiemeng
 * @date    2017/1/3
 */
@XStreamAlias("SOAP-ENV:Envelope")
public class ParseXmlResp {
    private static final String TAG = "ParseXmlResp";

    public Body Body = new Body();

    public static class Body {
        public Fjs Fjs = new Fjs();
    }

    public static class Fjs {
        public FjsResponse FjsResponse = new FjsResponse();
    }

    public static class FjsResponse {
        public Response Response = new Response();
    }

    public static class Response {
        public Execution Execution = new Execution();

        public ResponseContent ResponseContent = new ResponseContent();
    }

    public static class Execution {
        public Status Status = new Status();
    }

    public static class Status {
        @XStreamAsAttribute
        public String code = null;

        @XStreamAsAttribute
        public String sqlcode = null;

        @XStreamAsAttribute
        public String description = null;
    }

    public static class ResponseContent {
        public Parameter Parameter = new Parameter();

        public Document Document = new Document();
    }

    public static class Parameter {
        public List<Record> Record = new ArrayList<Record>();
    }

    public static class Document {
        public List<RecordSet> RecordSet = new ArrayList<RecordSet>();
    }

    public static class RecordSet {
        @XStreamAsAttribute
        public String id = null;

        public Master Master = new Master();

        public List<Detail> Detail = new ArrayList<Detail>();
    }

    public static class Master {
        @XStreamAsAttribute
        public String name = null;

        public Record Record = new Record();
    }

    public static class Detail {
        @XStreamAsAttribute
        public String name = null;

        public List<Record> Record = new ArrayList<Record>();
    }

    public static class Record {
        public List<Field> Field = new ArrayList<Field>();
    }

    public static class Field {
        @XStreamAsAttribute
        public String name = null;

        @XStreamAsAttribute
        public String value = null;

    }

    /**
     * 处理返回的数据成为xml格式
     *  需要判断是否为null
     * @param respType 节点名
     * @param payload  具体报文
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static ParseXmlResp fromXml(String respType,String payload) {
        LogUtils.i(TAG, "fromXml" + "start xml");
        ParseXmlResp resp = null;
        try
        {
            XStream xStream = new XStream(new XppDriver(new XmlFriendlyNameCoder("_-", "_")));
            xStream.alias("SOAP-ENV:Envelope", ParseXmlResp.class);

            xStream.aliasField("SOAP-ENV:Body", ParseXmlResp.class, "Body");
            xStream.aliasField("fjs1:" + respType+"Response", Body.class, "Fjs");
            xStream.aliasField("fjs1:response", Fjs.class, "FjsResponse");
            xStream.addImplicitCollection(Document.class, "RecordSet");
            xStream.addImplicitCollection(Parameter.class, "Record");
            xStream.addImplicitCollection(Record.class, "Field");
            xStream.addImplicitCollection(RecordSet.class, "Detail");
            xStream.addImplicitCollection(Detail.class, "Record");


            xStream.alias("RecordSet", RecordSet.class);
            xStream.alias("Detail", Detail.class);
            xStream.alias("Record", Record.class);
            xStream.alias("Field", Field.class);
            xStream.autodetectAnnotations(true);

            String xml = replaceSpecial(payload);
            LogUtils.i(TAG, xml);

            resp = (ParseXmlResp) xStream.fromXML(xml);
        }
        catch (Exception e) {
            LogUtils.e(TAG, "fromXml报错-->"+payload);
            return resp;
        }

        LogUtils.i(TAG, "fromXml" + "end xml");
        return resp;
    }

    private static String replaceSpecial(String origin) {
        String ret = origin.replace("&lt;", "<").replace("&gt;", ">").trim();
        return ret;
    }

    /**
     * 获取返回码
     *
     * @return
     */
    public String getCode() {
        String code = "getCode";
        try {
            code = Body.Fjs.FjsResponse.Response.Execution.Status.code;
        } catch (Exception e) {
            LogUtils.e(TAG, "getCode报错");
            return code;
        }
        LogUtils.i(TAG,"code="+code);
        return code;
    }

    /**
     * 获取错误描述
     *
     * @return
     */
    public String getDescription() {
        String description = "";
        try {
            description = Body.Fjs.FjsResponse.Response.Execution.Status.description;
        } catch (Exception e) {
            LogUtils.e(TAG, "getDescription报错");
            return description;
        }
        return description;
    }

    /**
     * 当在parameter中返回单号时使用该方法获取唯一单号
     *
     * @return
     */
    public String getFieldString() {
        String str = "";
        try {
            str = Body.Fjs.FjsResponse.Response.ResponseContent.Parameter.Record.get(0).Field.get(0).value;
        } catch (Exception e) {
            LogUtils.e(TAG, "getFieldString报错");
            return str;
        }
        return str;
    }


    /**
     * 获取RecordSet的master
     * 每个recordset中只能有一个master
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getMasterDatas(Class<T> clazz) {
        List<T> datas = new ArrayList<T>();
        try {
            List<RecordSet> record = Body.Fjs.FjsResponse.Response.ResponseContent.Document.RecordSet;
            if (null != record && record.size() != 0) {
                for (RecordSet set : record) {
                    T data = clazz.newInstance();
                    List<Field> fields = set.Master.Record.Field;
                    for (Field field : fields) {
                        setFieldValue(data, field);
                    }
                    datas.add(data);
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getMasterDatas报错");
            return datas;
        }
        return datas;
    }

    /**
     * 获取RecordSet的Detail
     * 每个recordset中只能有一个Detail
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getDetailDatas(Class<T> clazz) {
        List<T> datas = new ArrayList<T>();
        try {
            List<RecordSet> recordSets = Body.Fjs.FjsResponse.Response.ResponseContent.Document.RecordSet;
            if (null != recordSets && recordSets.size() != 0) {

                for (RecordSet set : recordSets) {
                    List<Record> record = set.Detail.get(0).Record;
                    if (null != record && record.size() != 0) {
                        for (int i = 0; i < record.size(); i++) {
                            T data = clazz.newInstance();
                            List<Field> fields = record.get(i).Field;
                            for (Field field : fields) {
                                setFieldValue(data, field);
                            }
                            datas.add(data);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getDetailDatas异常");
            return datas;
        }
        return datas;
    }

    /**
     * 获取多个RecordSet的指定的一个Detail集合
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getDetailDatas(String detailName, Class<T> clazz) {
        List<T> datas = new ArrayList<T>();
        try {
            List<RecordSet> recordSets = Body.Fjs.FjsResponse.Response.ResponseContent.Document.RecordSet;
            if (null != recordSets && recordSets.size() != 0) {
                for (RecordSet set : recordSets) {
                    List<Detail> details = set.Detail;
                    for (Detail detail : details) {
                        if (detailName.equals(detail.name)) {
                            List<Record> record = detail.Record;
                            if (null != record && record.size() != 0) {
                                for (int i = 0; i < record.size(); i++) {
                                    T data = clazz.newInstance();
                                    List<Field> fields = record.get(i).Field;
                                    for (Field field : fields) {
                                        setFieldValue(data, field);
                                    }
                                    datas.add(data);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getDetailDatas异常");
            return datas;
        }
        return datas;
    }

    /**
     * 获取Parameter中集合
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getParameterDatas(Class<T> clazz) {

        List<T> datas = new ArrayList<T>();
        try {
            List<Record> record = Body.Fjs.FjsResponse.Response.ResponseContent.Parameter.Record;
            if (null != record && record.size() != 0) {
                for (Record set : record) {
                    T data = clazz.newInstance();
                    List<Field> fields = set.Field;
                    for (Field field : fields) {
                        setFieldValue(data, field);
                    }
                    datas.add(data);
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, "getParameterDatas异常");
            return datas;
        }
        return datas;
    }

    /**
     * 给对象赋值
     *
     * @param data
     * @param field
     * @see [类、类#方法、类#成员]
     */
    private void setFieldValue(Object data, Field field) {
        try {
            java.lang.reflect.Field reflect_fieldField = data.getClass().getDeclaredField(field.name);
            reflect_fieldField.setAccessible(true);
            reflect_fieldField.set(data, field.value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
