package fast.mock.test.core.log;


import org.apache.maven.plugin.logging.SystemStreamLog;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 陈贵勇
 * @date 2021/6/29 新建
 */
public class MySystemStreamLog extends SystemStreamLog {
    private static boolean debugEnabled = false;

    @Override
    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public static void setDebugEnabled(boolean debugEnabled) {
        MySystemStreamLog.debugEnabled = debugEnabled;
    }

    public void debug(CharSequence content) {
        if (this.isDebugEnabled()) {
            this.print("debug", content);
        }
    }

    public void debug(CharSequence content, Throwable error) {
        if (this.isDebugEnabled()) {
            this.print("debug", content, error);
        }
    }

    public void debug(Throwable error) {
        if (this.isDebugEnabled()) {
            this.print("debug", error);
        }
    }

    private void print(String prefix, CharSequence content) {
        System.out.println("[" + prefix + "] " + content.toString());
    }

    private void print(String prefix, Throwable error) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        error.printStackTrace(pWriter);
        System.out.println("[" + prefix + "] " + sWriter.toString());
    }

    private void print(String prefix, CharSequence content, Throwable error) {
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        error.printStackTrace(pWriter);
        System.out.println("[" + prefix + "] " + content.toString() + "\n\n" + sWriter.toString());
    }
}
