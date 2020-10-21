package ink.fujisann.learning.base.utils.common;

import lombok.Data;

/**
 * @program: sqlleaning
 * @description: 封装返还结果
 * @author: hulei
 * @create: 2020-03-14 23:01:16
 */
@Data
public class Result<T> {

    private T data;
    private String code;
    private String msg;

    public Result(ResultBuild<T> resultBuild) {
        this.msg = resultBuild.msg;
        this.code = resultBuild.code;
        this.data = resultBuild.data;
    }

    public static class ResultBuild<T> {

        private String msg;
        private String code;
        private T data;

        public ResultBuild<T> setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public ResultBuild<T> setCode(String code) {
            this.code = code;
            return this;
        }

        public ResultBuild<T> setData(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            return new Result(this);
        }
    }
}
