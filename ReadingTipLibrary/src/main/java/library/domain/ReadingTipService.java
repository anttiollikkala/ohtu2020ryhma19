package library.domain;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import library.dao.ReadingTipDao;
import library.dao.ReadingTipDatabaseDao;

/**
 * Provides methods for handling ReadingTips.
 */
public class ReadingTipService {

    private ReadingTipDao readingTipDao;

    public ReadingTipService() {
        readingTipDao = new ReadingTipDatabaseDao("jdbc:sqlite:readingtip.db");
    }

    /**
     * Creates ReadingTip.
     *
     * @param type The type of the ReadingTip
     * @param title title
     * @param info1 The content of this field will depend on the type.
     * @param info2 The content of this field will depend on the type.
     */
    public ReadingTip createTip(String type, String title) throws Exception {
        ReadingTip rt = createTipWithType(type, title);
//        rt.setMoreInfo1(info1);
//        rt.setMoreInfo2(info2);
        readingTipDao.addTip(rt);
        return rt;
    }

    /**
     * Lists all ReadingTips.
     *
     * @return A list of all ReadingTips.
     */
    public List<ReadingTip> browseReadingTips() throws Exception {
        List<ReadingTip> tipList = readingTipDao.getAllTips();
        return tipList;
    }

    public List<ReadingTip> findTipByAuthor(String author) throws Exception {
        List<ReadingTip> tipList = readingTipDao.getTipByAuthor(author);

        return tipList;

    }

    private ReadingTip createTipWithType(String type, String title) {

        ReadingTip tip;

        if (type.equals("book")) {
            tip = new BookTip(title);
        } else if (type.equals("blogpost")) {
            tip = new BlogPostTip(title);
        } else if (type.equals("video")) {
            tip = new VideoTip(title);
        } else {
            tip = new PodcastTip(title);
        }

        return tip;
    }

    public void removeTip(String id) throws Exception {
        readingTipDao.removeTip(id);
    }
}
