package ViewModel.Commands;

import javax.swing.*;

public class ShowStatisticsCommand implements ICommand {
    private ViewModel.VMUser vmUser;
    static int nr = 0;

    public ShowStatisticsCommand(ViewModel.VMUser vmUser) {
        this.vmUser = vmUser;
    }



    @Override
    public void execute(int ok) throws java.sql.SQLException {

        Model.MoviePersistance per = new Model.MoviePersistance();
        per.generate();

        vmUser.getLabel11().set(new ImageIcon("chart_column" + nr + ".jpg"));
        vmUser.getLabel12().set(new ImageIcon("chart_column2" + nr + ".jpg"));
        vmUser.getLabel21().set(new ImageIcon("chart_radial" + nr + ".jpg"));
        vmUser.getLabel22().set(new ImageIcon("chart_radial2" + nr + ".jpg"));
        vmUser.getLabel31().set(new ImageIcon("chart_inelar" + nr + ".jpg"));
        vmUser.getLabel32().set(new ImageIcon("chart_inelar2" + nr + ".jpg"));
        nr++;

    }
}
