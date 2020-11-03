package ink.fujisann.learning.base.utils.common;

import lombok.Data;

/**
 * 封装返还结果
 *
 * @author hulei
 * @date 2020-03-14 23:01:16
 */
@Data
public class Result<T> {

    private T data;
    private String code;
    private String msg;

    public Result(Build<T> resultBuild) {
        this.msg = resultBuild.msg;
        this.code = resultBuild.code;
        this.data = resultBuild.data;
    }

    /**
     * build内部类
     *
     * @author hulei
     * @date 2020/11/3
     */
    public static class Build<T> {

        private String msg;
        private String code;
        private T data;

        public Build<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Build<T> code(String code) {
            this.code = code;
            return this;
        }

        public Build<T> data(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            return new Result<>(this);
        }
    }
}
