package ViewModel;

import ViewModel.Commands.*;
import net.sds.mvvm.properties.*;
import lombok.Getter;
import lombok.Setter;
import javax.swing.ImageIcon;

import javax.swing.table.DefaultTableModel;

@Getter
@Setter
public class VMUser {
    private ICommand addMovieCommand;
    private ICommand showMovieCommand;
    private ICommand deleteMovieCommand;
    private ICommand saveReportsCommand;
    private ICommand updateMovieCommand;
    private ICommand searchMovieCommand;
    private ICommand filterMovieCommand;
    private ICommand showStatisticsCommand;

    private Property<String> title = PropertyFactory.createProperty("text", this, String.class);
    private Property<String> year = PropertyFactory.createProperty("text1", this, String.class);
    private Property<String> category = PropertyFactory.createProperty("text2", this, String.class);
    private Property<DefaultTableModel> model = PropertyFactory.createProperty("model", this, new javax.swing.table.DefaultTableModel());
    private Property<Integer> row = PropertyFactory.createProperty("row", this, Integer.class);

    private Property<Boolean> artistic = PropertyFactory.createProperty("artistic", this, Boolean.class);
    private Property<Boolean> serial = PropertyFactory.createProperty("serial", this, Boolean.class);

    private Property<String> comboCat = PropertyFactory.createProperty("comboCat", this, String.class);
    private Property<String> comboYear = PropertyFactory.createProperty("comboYear", this, String.class);
    private Property<String> comboType = PropertyFactory.createProperty("comboType", this, String.class);

    private Property<ImageIcon> label11 = PropertyFactory.createProperty("label11", this, ImageIcon.class);
    private Property<ImageIcon> label12 = PropertyFactory.createProperty("label12", this, ImageIcon.class);
    private Property<ImageIcon> label21 = PropertyFactory.createProperty("label21", this,ImageIcon.class);
    private Property<ImageIcon> label22 = PropertyFactory.createProperty("label22", this, ImageIcon.class);
    private Property<ImageIcon> label31 = PropertyFactory.createProperty("label31", this, ImageIcon.class);
    private Property<ImageIcon> label32 = PropertyFactory.createProperty("label32", this, ImageIcon.class);




    public VMUser() {
        this.addMovieCommand = new ViewModel.Commands.AddCommand(null,this);
        this.showMovieCommand = new ViewModel.Commands.ShowCommand(null,this);
        this.deleteMovieCommand = new ViewModel.Commands.DeleteCommand(null,this);
        this.saveReportsCommand = new ViewModel.Commands.SaveReportsCommand(this);
        this.updateMovieCommand = new ViewModel.Commands.UpdateCommand(null,this);
        this.searchMovieCommand = new ViewModel.Commands.SearchMovieCommand(this);
        this.filterMovieCommand = new FilterMovieCommand(this);
        this.showStatisticsCommand = new ViewModel.Commands.ShowStatisticsCommand(this);
    }
    public void setModel(DefaultTableModel defaultTableModel) {
        model.set(defaultTableModel);
    }
}
