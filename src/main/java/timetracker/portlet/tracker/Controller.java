package timetracker.portlet.tracker;

import juzu.Path;
import juzu.Resource;
import juzu.View;
import juzu.template.Template;
import org.exoplatform.portal.webui.util.Util;
import timetracker.ChromatticService;
import timetracker.TrackerService;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
  @Path("tasks.gtmpl")
  Template tasksTemplate;

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
    String size = portletPreferences.getValue("size", "1024");
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("username", Util.getPortalRequestContext().getRemoteUser());

    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    parameters.put("now", df.format(c.getTime()));
    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    df = new SimpleDateFormat("dd/MM");
    parameters.put("monday", df.format(c.getTime()));
    parameters.put("columns", trackerService_.getColumns());


    indexTemplate.render(parameters);
  }

  @Resource
  public void getTasks()
  {
    System.out.println("getTasks");
    tasksTemplate.render();
  }

}
