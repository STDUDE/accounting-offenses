package org.fr2eman.accountingoffenses.model;

/**
 * Created by fr2eman on 08.04.2016.
 */
public class ArticleModel {

    public static final String STRING_KoAP = "КоАП";
    public static final String STRING_PIKoAP = "ПИКоАП";

    private static final String KoAP = "коап";
    private static final String PIKoAP = "пикоап";

    public static final int ID_KoAP = 1;
    public static final int ID_PIKoAP = 2;

    public enum CodexType { KoAP, PiKoAP, EMPTY };

    private int id;
    private String numberArticle;
    private CodexType codexType;
    private String name;
    private String description;
    private boolean warning;
    private int minMulct;
    private int maxMulct;

    public ArticleModel() {
        this.id = 0;
        this.numberArticle = "";
        this.codexType = CodexType.EMPTY;
        this.name = "";
        this.description = "";
        this.warning = false;
        this.minMulct = 0;
        this.maxMulct = 0;
    }

    public ArticleModel(int id, String numberArticle, String codexType, String name,
                         String description, boolean warning, int minMulct, int maxMulct) {
        this.id = id;
        this.numberArticle = numberArticle;
        if(codexType.toLowerCase().equals(KoAP)) {
            this.codexType = CodexType.KoAP;
        } else if (codexType.toLowerCase().equals(KoAP)) {
            this.codexType = CodexType.PiKoAP;
        } else {
            this.codexType = CodexType.EMPTY;
        }
        this.name = name;
        this.description = description;
        this.warning = warning;
        this.minMulct = minMulct;
        this.maxMulct = maxMulct;
    }

    public ArticleModel(int id, String numberArticle, String codexType, String name) {
        this.id = id;
        this.numberArticle = numberArticle;
        if(codexType.toLowerCase().equals(KoAP)) {
            this.codexType = CodexType.KoAP;
        } else if (codexType.toLowerCase().equals(KoAP)) {
            this.codexType = CodexType.PiKoAP;
        } else {
            this.codexType = CodexType.EMPTY;
        }
        this.name = name;
        this.description = "";
        this.warning = false;
        this.minMulct = 0;
        this.maxMulct = 0;
    }

    public ArticleModel(String numberArticle, String codexType) {
        this.id = 0;
        this.numberArticle = numberArticle;
        if(codexType.toLowerCase().equals(KoAP)) {
            this.codexType = CodexType.KoAP;
        } else if (codexType.toLowerCase().equals(PIKoAP)) {
            this.codexType = CodexType.PiKoAP;
        } else {
            this.codexType = CodexType.EMPTY;
        }
        this.name = "";
        this.description = "";
        this.warning = false;
        this.minMulct = 0;
        this.maxMulct = 0;
    }

    public ArticleModel(String numberArticle, int codexId) {
        this.id = 0;
        this.numberArticle = numberArticle;
        switch(codexId) {
            case ID_KoAP: {
                this.codexType = CodexType.KoAP;
                break;
            }
            case ID_PIKoAP: {
                this.codexType = CodexType.PiKoAP;
                break;
            }
            default: {
                this.codexType = CodexType.EMPTY;
                break;
            }
        }

        this.name = "";
        this.description = "";
        this.warning = false;
        this.minMulct = 0;
        this.maxMulct = 0;
    }

    @Override
    public boolean equals(Object object) {
        ArticleModel article = (ArticleModel)object;
        if(this.numberArticle.equals(article.numberArticle)
                && this.codexType == article.codexType) {
            return true;
        } else return false;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }

    public void setNumberArticle(String number) {
        this.numberArticle = number;
    }
    public String getNumberArticle() {
        return this.numberArticle;
    }

    public void setCodexType(CodexType type) {
        this.codexType = type;
    }
    public String getCodexType() {
        if(this.codexType == CodexType.KoAP) {
            return "КоАП";
        } else if(this.codexType == CodexType.PiKoAP) {
            return "ПИКоАП";
        } else {
            return "";
        }
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }
    public boolean getWarning() {
        return this.warning;
    }

    public void setMinMulct(int minMulct) {
        this.minMulct = minMulct;
    }
    public int getMinMulct() {
        return this.minMulct;
    }

    public void setMaxMulct(int maxMulct) {
        this.maxMulct = maxMulct;
    }
    public int getMaxMulct() {
        return this.maxMulct;
    }

    public static String getNameCodexById(int id) {
        if(id == ID_KoAP) {
            return STRING_KoAP;
        } else if(id == ID_PIKoAP) {
            return STRING_PIKoAP;
        } else {
            return "";
        }
    }

    public static int getIDCodexByName(String name) {
        if(name.toLowerCase().equals(KoAP)) {
            return ID_KoAP;
        } else if(name.toLowerCase().equals(PIKoAP)) {
            return ID_PIKoAP;
        } else {
            return 0;
        }
    }

}
