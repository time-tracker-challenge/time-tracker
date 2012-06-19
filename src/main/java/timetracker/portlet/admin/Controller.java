package timetracker.portlet.admin;

import juzu.Path;
import juzu.View;
import juzu.template.Template;
import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import timetracker.ChromatticService;
import timetracker.model.Task;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
public class Controller
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  @Inject
  PortletPreferences portletPreferences;


  ChromatticService chromatticService_;

  Chromattic chromattic;

  @Inject
  public Controller(ChromatticService chromatticService)
  {
    chromatticService_ = chromatticService;
    chromattic = chromatticService_.init();
  }

    @View
  public void index() throws IOException
  {

    ChromatticSession session = chromattic.openSession();
    try
    {
      Task task = session.insert(Task.class, "tt-chromattic-ben-"+System.currentTimeMillis());
      task.setTitle("Ben's Test");
      session.save();
    }
    finally
    {
      session.close();
    }

    String size = "256";//
    System.out.println(portletPreferences.getValue("size", "1024"));

    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("size", size);
    indexTemplate.render(parameters);
  }

}
