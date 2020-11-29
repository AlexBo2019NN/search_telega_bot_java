/**
 * @author Alex Bocharov skype bam271074
 *         <p>
 *         Project under hackaton rzdhack.ru 27-29.11.2020
 *         <p>
 *         Weather telegram bot and ML solution
 */

public class Model {

    private String name;
    //private Double temp;
    //private Double humidity;
    private String icon;
    private String main;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}

