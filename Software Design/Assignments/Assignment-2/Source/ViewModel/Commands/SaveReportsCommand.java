package ViewModel.Commands;

public class SaveReportsCommand implements ICommand {
    private ViewModel.VMUser vmUser;

    public SaveReportsCommand(ViewModel.VMUser vmUser) {
        this.vmUser = vmUser;
    }

    @Override
    public void execute(int ok) throws java.sql.SQLException {
        Object[] options = {"Csv",//0
                "Json",//1
                "Xml"};//2
        int n = javax.swing.JOptionPane.showOptionDialog(null,
                "Choose an option",
                "",
                javax.swing.JOptionPane.DEFAULT_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
        Model.MoviePersistance per = new Model.MoviePersistance();
    if(n!=-1){
     per.saveReports(n);}

    }
}
