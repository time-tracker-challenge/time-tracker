package timetracker.portlet.admin;

import juzu.Action;
import juzu.Path;
import juzu.SessionScoped;
import juzu.View;
import juzu.template.Template;
import timetracker.ChromatticService;
import timetracker.TrackerService;
import timetracker.bean.ColumnRow;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class Controller extends juzu.Controller
{

  /** . */
  @Inject
  @Path("index.gtmpl")
  Template indexTemplate;

  TrackerService trackerService_;

  @Inject
  public Controller(ChromatticService chromatticService, TrackerService trackerService)
  {
    trackerService_ = trackerService;
    trackerService_.initChromattic(chromatticService.init());
  }

    @View
  public void index() throws IOException
  {
    indexTemplate.with().set("columns", trackerService_.getColumns()).render();
  }

  @Action
  public void updateData() throws ParseException
  {
    Map<String, String[]> params = actionContext.getParameters();
    if (params.containsKey("rows"))
    {
      List<ColumnRow> rows = new LinkedList<ColumnRow>();
      int irows = new Integer(params.get("rows")[0]);
      for (int i=0 ; i<irows ; i++)
      {
        ColumnRow row = new ColumnRow();
        row.setTitle(params.get("title_"+i)[0]);
        String[] values = params.get("values_"+i)[0].replaceAll(" ", "").split(",");
        row.setValues(values);
        rows.add(row);
      }

      trackerService_.updateColumns(rows);
    }
  }

}
