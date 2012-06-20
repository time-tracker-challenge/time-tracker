package timetracker;

import org.chromattic.api.Chromattic;
import org.chromattic.api.ChromatticSession;
import org.chromattic.ext.ntdef.NTFolder;
import org.exoplatform.portal.webui.util.Util;
import timetracker.model.Task;
import timetracker.model.User;
import timetracker.model.Week;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TrackerService {

  Chromattic chromattic_;

  public void initChromattic(Chromattic chromattic)
  {
    chromattic_ = chromattic;
    createApplicationData();
  }

  private void createApplicationData()
  {
    ChromatticSession session = chromattic_.openSession();
    try
    {
      NTFolder users = session.findByPath(NTFolder.class, "users");
      if (users==null)
      {
        users = session.insert(NTFolder.class, "users");
        session.save();
      }

      NTFolder columns = session.findByPath(NTFolder.class, "columns");
      if (columns==null)
      {
        columns = session.insert(NTFolder.class, "columns");
        session.save();
      }

    }
    finally
    {
      session.close();
    }

  }

  public void createDummyData()
  {
    ChromatticSession session = chromattic_.openSession();
    try
    {
      NTFolder users = session.findByPath(NTFolder.class, "users");

      String username = Util.getPortalRequestContext().getRemoteUser();
      User user = session.findByPath(User.class, "users/"+username);
      if (user!=null)
      {
        session.remove(user);
        session.save();
      }
      System.out.println("CREATING USER DATA FOR : "+username);
      user = session.insert(User.class, username);
      users.addChild(user);
      user.setUsername(username);

      Calendar c = Calendar.getInstance();
      c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
      String name = df.format(c.getTime());

      Week week1 = session.create(Week.class, name);
      week1.setParent(user);
      week1.setFirstDay(c.getTime());

      Task task = session.create(Task.class, "eXo-Presales-Weka");
      task.setParent(week1);
      task.setComment("working on a poc");

      Integer[] hours = new Integer[] {4, 0, 0, 0, 0};
      task.setHours(hours);

      Task task2 = session.create(Task.class, "eXo-Product-Juzu");
      task2.setParent(week1);
      task2.setComment("time tracker portlet");

      hours = new Integer[] {4, 8, 8, 0, 0};
      task2.setHours(hours);

      session.save();

    }
    finally
    {
      session.close();
    }


  }
}
