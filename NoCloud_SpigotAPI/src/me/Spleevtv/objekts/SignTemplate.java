package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class SignTemplate {

    private SignTemplateKind signkind;
    private Boolean hide;
    private Boolean animation;
    private String animationsplitter;
    private String[] lines;
    private HashMap<String, String[]> splittedlines = new HashMap<>();
    private Integer id, subid;
    private HashMap<String, Integer> currentupdateint = new HashMap<>();
    private ConcurrentHashMap<String, Integer> maxupdateint = new ConcurrentHashMap<>();

    public SignTemplate(SignTemplateKind kind) {
        this.signkind = kind;
        File file = new File(Main.instance.getDataFolder().getPath() + "/signTemplate.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.hide = cfg.getBoolean(this.signkind + ".Hide");
        this.animation = cfg.getBoolean(this.signkind + ".Animation");
        this.animationsplitter = cfg.getString(this.signkind + ".Animation-Splitter");
        this.lines = new String[]{
                cfg.getString(this.signkind + ".Line-1"),
                cfg.getString(this.signkind + ".Line-2"),
                cfg.getString(this.signkind + ".Line-3"),
                cfg.getString(this.signkind + ".Line-4")
        };
        String[] i = cfg.getString(this.signkind + ".Block").split(":");
        this.id = Integer.parseInt(i[0]);
        this.subid = Integer.parseInt(i[1]);
        if(this.animation) {
            int a = 0;
            for(String line : this.lines) {
                this.splittedlines.put(a + "", line.split(this.animationsplitter));
                this.maxupdateint.put(a + "", this.splittedlines.get(a + "").length -1);
                a++;
            }
        }
        this.currentupdateint.put("0", 0);
        this.currentupdateint.put("1", 0);
        this.currentupdateint.put("2", 0);
        this.currentupdateint.put("3", 0);
    }
    public Boolean isAnimation() {
        return animation;
    }
    public Boolean isHide() {
        return hide;
    }
    public Integer getBlockID() {
        return id;
    }
    public Integer getBlockSubID() {
        return subid;
    }
    public SignTemplateKind getSignKind() {
        return signkind;
    }
    public String getAnimationSplitter() {
        return animationsplitter;
    }
    public String[] getLines() {
        return lines;
    }
    public HashMap<String, Integer> getCurrentupdateint() {
        return currentupdateint;
    }
    public ConcurrentHashMap<String, Integer> getMaxupdateint() {
        return maxupdateint;
    }
    public String[] getSplittedLine(Integer line) {
        return splittedlines.get(line + "");
    }
    public void addCurrentUpdateInt(Integer line) {
        Integer end = this.currentupdateint.get(line + "") +1;
        if(end > this.maxupdateint.get(line + "")) {
            this.currentupdateint.put(line + "", 0);
        } else {
            this.currentupdateint.put(line + "", end);
        }
    }
}
