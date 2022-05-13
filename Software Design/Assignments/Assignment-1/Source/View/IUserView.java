package View;

import Model.Movie;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public interface IUserView {
    DefaultTableModel getModel();
    void setTable(DefaultTableModel model);
    String getCategory();
    void setCategory(String category1);
    int getYear();
    void setYear(String year1);
    String getTitle();
    void setTitle(String title1);
    String selectie();
    void showSearch(Movie movie);
    boolean artistSelect();
    boolean serialSelect();
    String  comboCategorySelect();
    String  comboTypeSelect();
    String  comboYearSelect();
    void setShow(List<Movie> movies);
}
