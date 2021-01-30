package ink.fujisann.learning.stock.util;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleBindings;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * 读取js函数进行计算
 *
 * @author raiRezon
 * @version 2021/1/30
 */
@Slf4j
public class JsEngine {
    public static ScriptEngine engine;
    public static String str;

    static {
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        engine = scriptEngineManager.getEngineByName("javascript");
    }

    public static void getValues() throws Exception {
        str = "  var msg='hello';          "
                + "  var number = 123;         "
                + "  var array=['A','B','C'];  "
                + "  var json={                "
                + "      'name':'pd',          "
                + "      'subjson':{           "
                + "           'subname':'spd'  "
                + "           ,'id':123        "
                + "           }                "
                + "      };                    ";
        engine.eval(str);
        str = "msg+=' world';number+=5";
        System.out.println(engine.get("msg"));
        System.out.println(engine.get("number"));
        //获取数组
        ScriptObjectMirror array = (ScriptObjectMirror) engine.get("array");
        System.out.println(array.getSlot(0));

        ScriptObjectMirror json = (ScriptObjectMirror) engine.get("json");
        System.out.println(json.get("name"));

        //json嵌套
        ScriptObjectMirror subjson = (ScriptObjectMirror) json.get("subjson");
        System.out.println(subjson.get("subname"));
    }

    public static void getObject() throws Exception {
        str = "  var obj=new Object();     "
                + "  obj.info='hello world';   "
                + "  obj.getInfo=function(){   "
                + "        return this.info;   "
                + "  };                        ";
        engine.eval(str);
        ScriptObjectMirror obj = (ScriptObjectMirror) engine.get("obj");
        System.out.println(obj.get("info"));
        System.out.println(obj.get("getInfo"));
        str = "obj.getInfo()";
        System.out.println(engine.eval(str));
    }

    //给js传递变量
    public static void putValue() throws Exception {
        str = "Math.pow(a,b)";
        Map<String, Object> input = new TreeMap<>();
        input.put("a", 2);
        input.put("b", 8);
        System.out.println(engine.eval(str, new SimpleBindings(input)));
    }

    //调用js函数
    public static void callJSFunction() throws Exception {
        str = "function add (a, b) {return a+b; }";
        //执行js脚本定义函数
        engine.eval(str);
        Invocable invocable = (Invocable) engine;
        Object res = invocable.invokeFunction("add", new Object[]{2, 3});
        System.out.println(res);

    }

    //读取js文件，执行函数;易变业务使用脚本编写，这样即使修改脚本，也不需重新部署java程序
    @SneakyThrows
    public static String getThsCookie() {
        //模拟执行期间add.js被修改
        String path = JsEngine.class.getResource("/js/aes.min.js").getPath();
        engine.eval(new FileReader(path));
        Invocable invocable = (Invocable) engine;
        long now = System.currentTimeMillis();
        String s = now + ".123";
        String result = (String) invocable.invokeFunction("v", new Object[]{s});
        log.debug(">>> 本地执行js获取到的token {}" + result);
        return result;
    }

    public static void main(String[] args) throws Exception {
        getValues();
        getObject();
        putValue();
        callJSFunction();
        System.out.println(getThsCookie());
    }

}
