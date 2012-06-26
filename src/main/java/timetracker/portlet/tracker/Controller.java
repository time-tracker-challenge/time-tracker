package timetracker.portlet.tracker;

import juzu.*;
import juzu.template.Template;
import org.exoplatform.portal.webui.util.Util;
import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.services.organization.User;
import org.exoplatform.services.organization.UserHandler;
import timetracker.ChromatticService;
import timetracker.TrackerService;
import timetracker.bean.TaskRow;
import timetracker.model.Task;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class Controller extends juzu.Controller
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

  String userFullname = "";

  @Inject
  public Controller(ChromatticService chromatticService, TrackerService trackerService, OrganizationService organizationService)
  {
    trackerService_ = trackerService;
    trackerService_.initChromattic(chromatticService.init());
    setUserFullname(organizationService);
    //trackerService_.createDummyData();
  }

  private void setUserFullname(OrganizationService organizationService)
  {
    try
    {
      String username = Util.getPortalRequestContext().getRemoteUser();
      if (username!=null)
      {
        UserHandler userHandler = organizationService.getUserHandler();
        User user = userHandler.findUserByName(username);
        userFullname = user.getFullName();
      }
    }
    catch (Exception e)
    {
    }

  }

  @View
  public void index() throws IOException
  {
    String size = portletPreferences.getValue("size", "1024");
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("username", Util.getPortalRequestContext().getRemoteUser());
    parameters.put("fullname", userFullname);

    Calendar c = Calendar.getInstance();
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    parameters.put("now", df.format(c.getTime()));
    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    df = new SimpleDateFormat("dd/MM/yyyy");
    parameters.put("monday", df.format(c.getTime()));
    parameters.put("columns", trackerService_.getColumns());


    indexTemplate.render(parameters);
  }

  @Resource
  public void refreshAll(String from, String diff) throws Exception
  {
    Calendar cal=Calendar.getInstance();
    SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
    Date d1=df.parse(from);
    cal.setTime(d1);
    if (diff!=null)
    {
      Integer di = new Integer(diff);
      cal.add(Calendar.DATE, 7*di);
    }
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("username", Util.getPortalRequestContext().getRemoteUser());
    parameters.put("fullname", userFullname);

    parameters.put("now", df.format(cal.getTime()));
    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    df = new SimpleDateFormat("dd/MM/yyyy");
    parameters.put("monday", df.format(cal.getTime()));
    parameters.put("columns", trackerService_.getColumns());

    indexTemplate.render(parameters);
  }

  @Resource
  public void getTasks(String from, String diff) throws ParseException {
    Calendar cal=Calendar.getInstance();
    SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
    Date d1=df.parse(from);
    cal.setTime(d1);
    if (diff!=null)
    {
      Integer di = new Integer(diff);
      cal.add(Calendar.DATE, 7*di);
    }
    List<Task> tasks = trackerService_.getTasks(cal);

    tasksTemplate.with().set("tasks", tasks).set("nbColumns", trackerService_.getColumns().size()).render();
  }

  @Action
  public void updateData() throws ParseException
  {
    Map<String, String[]> params = actionContext.getParameters();

    if (params.containsKey("from") && params.containsKey("rows"))
    {
      Calendar cal=Calendar.getInstance();
      SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
      Date d1=df.parse(params.get("from")[0]);
      cal.setTime(d1);
      int irows = new Integer(params.get("rows")[0]);
      List<TaskRow> rows = new LinkedList<TaskRow>();
      for (int i=0 ; i<irows ; i++)
      {
        TaskRow row = new TaskRow();
        row.setColumns(params.get("column_"+i));
        row.setHours(params.get("hours_"+i));
        rows.add(row);
      }

      trackerService_.updateData(cal, rows);
    }
  }

}
