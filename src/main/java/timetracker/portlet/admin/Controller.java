package timetracker.portlet.admin;

import juzu.Path;
import juzu.SessionScoped;
import juzu.View;
import juzu.template.Template;
import org.chromattic.api.Chromattic;
import timetracker.ChromatticService;
import timetracker.TrackerService;

import javax.inject.Inject;
import javax.portlet.PortletPreferences;
import java.io.IOException;
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
    indexTemplate.render(parameters);
  }

}
