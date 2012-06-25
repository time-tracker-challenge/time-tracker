package timetracker.portlet.export;

import juzu.Action;
import juzu.Path;
import juzu.SessionScoped;
import juzu.View;
import juzu.template.Template;
import timetracker.ChromatticService;
import timetracker.TrackerService;

import javax.inject.Inject;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/** @author <a href="mailto:benjamin.paillereau@exoplatform.com">Benjamin Paillereau</a> */
@SessionScoped
public class Controller
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
    Map<String, Object> parameters = new HashMap<String, Object>();

    parameters.put("columns", trackerService_.getColumns());

    indexTemplate.render(parameters);
  }

  @Action
  public void updateData(String data) throws ParseException
  {
    trackerService_.updateColumns(data);
  }

}
