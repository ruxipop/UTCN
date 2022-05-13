package triggers;
import net.sds.mvvm.bindings.Binding;
import net.sds.mvvm.bindings.Direction;
import net.sds.mvvm.triggers.Trigger;
import javax.swing.*;


public class ComboBoxChange implements Trigger  {
    private JComboBox comboBox;
    public ComboBoxChange(JComboBox comboBox){
        this.comboBox = comboBox;
    }
    public void register(Binding binding, Direction direction) {
        this.comboBox.addItemListener((e) -> {
            binding.apply(direction);
        });

    }
}
