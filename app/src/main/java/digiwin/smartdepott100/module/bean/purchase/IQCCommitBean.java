package digiwin.smartdepott100.module.bean.purchase;

import java.util.List;

/**
 * Created by maoheng on 2017/8/15.
 */

public class IQCCommitBean {
    private List<IQCCommitItemBean> data;

    public List<IQCCommitItemBean> getData() {
        return data;
    }

    public void setData(List<IQCCommitItemBean> data) {
        this.data = data;
    }

    public static class IQCCommitItemBean {
        /**
         * 检验单号
         */
        private String doc_no;
        /**
         * 行序
         */
        private String seq;
        /**
         * 缺点数
         */
        private String defect_qty;
        /**
         * 不良数
         */
        private String defect_reason_qty;
        /**
         * 判定结果
         */
        private String qc_result;
        /**
         * 备注
         */
        private String remark;

        public String getDoc_no() {
            return doc_no;
        }

        public void setDoc_no(String doc_no) {
            this.doc_no = doc_no;
        }

        public String getSeq() {
            return seq;
        }

        public void setSeq(String seq) {
            this.seq = seq;
        }

        public String getDefect_qty() {
            return defect_qty;
        }

        public void setDefect_qty(String defect_qty) {
            this.defect_qty = defect_qty;
        }

        public String getDefect_reason_qty() {
            return defect_reason_qty;
        }

        public void setDefect_reason_qty(String defect_reason_qty) {
            this.defect_reason_qty = defect_reason_qty;
        }

        public String getQc_result() {
            return qc_result;
        }

        public void setQc_result(String qc_result) {
            this.qc_result = qc_result;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}

