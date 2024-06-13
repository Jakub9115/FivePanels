package at.spengergasse.fivepanels.testview;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "test")
@PageTitle("Test")
public class TestView extends VerticalLayout {

    public TestView() {

        add(new Text("text"));
    }
}
