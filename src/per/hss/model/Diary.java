package per.hss.model;

import java.util.Date;

public class Diary {
    private int diaryId;
    private String   title;
    private String content;
    private int typeId=-1;
    private String typeName;
    private Date releaseDate;
    private String releaseDateStr;
    private int diaryCount;

    public Diary() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Diary(String title, String content, int typeId) {
        super();
        this.title = title;
        this.content = content;
        this.typeId = typeId;
    }

    public int getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(int diaryId) {
        this.diaryId = diaryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDateDate) {
        this.releaseDate= releaseDateDate;
    }

    public String getReleaseDateStr() {
        return releaseDateStr;
    }

    public void setReleaseDateStr(String releaseDateStr) {
        this.releaseDateStr = releaseDateStr;
    }

    public int getDiaryCount() {
        return diaryCount;
    }

    public void setDiaryCount(int diaryCount) {
        this.diaryCount = diaryCount;
    }
}
