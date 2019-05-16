package my;

/**
 * @author wei.zhan@hand-china.com
 * @version 1.0
 * @since 1.0
 * 2018/11/12
 */
public class BTreeCmd {
    public String cmd;
    public TreeNode node;

    public BTreeCmd(String cmd, TreeNode node) {
        this.cmd = cmd;
        this.node = node;
    }
    public static final String GO = "GO";
    public static final String PRINT = "PRINT";

}
