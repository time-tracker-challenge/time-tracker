package timetracker.portlet.tracker;

import juzu.Path;
import juzu.View;
import juzu.template.Template;
import timetracker.ChromatticService;
import timetracker.TrackerService;

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


  TrackerService trackerService_;

  @Inject
  public Controller(ChromatticService chromatticService, TrackerService trackerService)
  {
    trackerService_ = trackerService;
    trackerService_.initChromattic(chromatticService.init());
    trackerService_.createDummyData();
  }

    @View
  public void index() throws IOException
  {
    System.out.println("Time Tracker Application");
    String size = portletPreferences.getValue("size", "1024");
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("size", "Time Tracker Application : "+size);
    indexTemplate.render(parameters);
  }

}
