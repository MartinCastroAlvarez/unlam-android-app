package unlam.cal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Integer;
import android.util.Log;

public class Event {

	static DateFormat dateformat 			= new SimpleDateFormat("dd/MM/yyyy");
	private String title 				= "";
	private String subtitle				= "";
	private long date 				= 0;
	private int type 				= 0;

	public static int UNKNOWN			= 0;
	public static int CAMBIO_CARRERA 		= 1;
	public static int PASE_DE_UNIVERSIDAD		= 2;
	public static int INSCRIPCION_CURSO_INGRESO	= 3;
	public static int INI_DE_CLASES_CURSO_INGRESO 	= 4;
	public static int FIN_DE_CLASES_CURSO_INGRESO	= 5;
	public static int EXAMENES_CURSO_DE_INGRESO	= 6;
	public static int INSC_MEDICINA			= 7;
	public static int INSC_FORMACION_CONTINUA	= 8;
	public static int INSC_HUMANIDADES		= 9;
	public static int INSC_DERECHO			= 10;
	public static int INSC_SALUD			= 11;
	public static int INSC_ECONOMICAS		= 12;
	public static int INSC_INGENIERIA		= 13;
	public static int INSC_APM			= 14;
	public static int INSC_TODOS_LOS_DEPARTAMENTOS	= 15;
	public static int VERIFICACION_INSCRIPCION	= 16;
	public static int INI_DE_CLASES			= 17;
	public static int INI_DE_CLASES_INGRESANTES	= 18;
	public static int FIN_DE_CLASES			= 19;
	public static int INCRIPCION_FINALES		= 20;
	public static int VERIFICACION_FINALES		= 21;
	public static int FECHA_FINALES			= 22;

        public static Pattern INI_CLASES       = Pattern.compile(".*(Inicio de clases)$");
        public static Pattern INI_INGRESANTES  = Pattern.compile(".*(Inicio de clases de ingresantes).*");
        public static Pattern FIN_CLASES       = Pattern.compile(".*(Finalización de clases).*");
        public static Pattern INSC_FINALES     = Pattern.compile(".*(Inscripción a finales).*");
        public static Pattern VERIFICACION     = Pattern.compile(".*(Verificación).*");
        public static Pattern EXAMENES         = Pattern.compile(".*(Ex.menes).*");
        public static Pattern INSCRIPCION      = Pattern.compile(".*(Inscripci.n).*");
        public static Pattern INI_C_INGRESO    = Pattern.compile(".*(Inicio Curso de Ingreso).*");
        public static Pattern FIN_C_INGRESO    = Pattern.compile(".*(Finalización Curso de Ingreso).*");
        public static Pattern MEDICINA         = Pattern.compile(".*(Carrera de Medicina).*");
        public static Pattern FORM_CONTINUA    = Pattern.compile(".*(Formación Continua).*");
        public static Pattern HUMANIDADES      = Pattern.compile(".*(Humanidades).*");
        public static Pattern DERECHO          = Pattern.compile(".*(Derecho).*");
        public static Pattern SALUD            = Pattern.compile(".*(Salud).*");
        public static Pattern ECONOMICAS       = Pattern.compile(".*(Econ.micas).*");
        public static Pattern INGENIERIA       = Pattern.compile(".*(Ingenier.a).*");
        public static Pattern EXTENSION        = Pattern.compile(".*(Secretar.a de Extensi.n).*");
        public static Pattern TODOS            = Pattern.compile(".*(Todos los Departamentos).*");
        public static Pattern RECEPCION        = Pattern.compile(".*(Recepci.n de documentaci.n).*");
	
	public static Pattern ONE_DATE	= Pattern.compile("^(\\d+) de (\\w+) de (\\d+)");
	public static Pattern TO1_DATE	= Pattern.compile("^(\\d+) al (\\d+) de (\\w+) de (\\d+)");	
	public static Pattern TO2_DATE	= Pattern.compile("^(\\d+) de (\\w+) al (\\d+) de (\\w+) de (\\d+)");
	public static Pattern AND_DATE	= Pattern.compile("^(\\d+) y (\\d+) de (\\w+) de (\\d+)");
	public static Pattern COMA_DATE = Pattern.compile("^(\\d+), (\\d+) y (\\d+) de (\\w+) de (\\d+)");

	public void setType(int t) 		{ type = t; }
	public int getType() 			{ return type; }

	public String getTitle() 		{ return title; }
	public void setTitle(String t) 		{ title = t; }	

	public String getSubtitle() 		{ return subtitle; }
	public void setSubtitle(String t) 	{ subtitle = t; }	
	public boolean hasSubtitle()		{ return subtitle.length()>0; }

	public void setDate(Date d)     	{ date = d.getTime(); }
	public Date getDate()           {
                                                Date d = new Date();
                                                d.setTime(date*1000);
                                                return d;
                                        }

	public void setLong(long i)     	{ date = i; }
        public long getLong()           	{ return date; }

        public String dateToString()    	{ return dateformat.format(getDate()); }

	public boolean setDate(String s) {

		setSubtitle(s);
		Date d;
		Matcher matcher1 = Event.ONE_DATE.matcher(s);
		Matcher matcher2 = Event.TO1_DATE.matcher(s);
		Matcher matcher3 = Event.TO2_DATE.matcher(s);
		Matcher matcher4 = Event.AND_DATE.matcher(s);
		Matcher matcher5 = Event.COMA_DATE.matcher(s);

		/* Evaluate 21 de agosto de 2014 */ 
		if (matcher1.find())  {
			String day   	= matcher1.group(1);
			String month 	= matcher1.group(2);
			String year  	= matcher1.group(3);
			d		= Event.date(day,month,year);
		}

		/* Evaluate 17 al 20 de noviembre de 2014 */ 
		else if (matcher2.find())  {
			String day1  	= matcher2.group(1);
			String day2  	= matcher2.group(2);
			String month 	= matcher2.group(3);
			String year  	= matcher2.group(4);
			d		= Event.date(day1,month,year);
		}

		/* Evaluate 2 de abril al 24 de octubre de 2014 */ 
		else if (matcher3.find())  {
			String day1   	= matcher3.group(1);
			String month1 	= matcher3.group(2);
			String day2   	= matcher3.group(3);
			String month2 	= matcher3.group(4);
			String year   	= matcher3.group(5);
			d		= Event.date(day1,month1,year);
		}

		/* Evaluate 19 y 20 de noviembre de 2014 */ 
		else if (matcher4.find())  {
			String day1  	= matcher4.group(1);
			String day2  	= matcher4.group(2);
			String month 	= matcher4.group(3);
			String year  	= matcher4.group(4);
			d		= Event.date(day1,month,year);
		}

		/* Evaluate 18, 19 y 20 de noviembre de 2014 */ 
		else if (matcher5.find())  {
			String day1  	= matcher5.group(1);
			String day2  	= matcher5.group(2);
			String day3  	= matcher5.group(3);
			String month 	= matcher5.group(4);
			String year  	= matcher5.group(5);
			d		= Event.date(day1,month,year);
		}

		else {
			return false;
		}

		setDate(d);
		return true;

	}

	private static Date date(String d, String m, String y) {
		int day   = Integer.parseInt(d);
		int year  = Integer.parseInt(y);
		int month   = 0;
		if (m=="enero") 		month=1;
		else if (m=="febrero") 		month=2;
		else if (m=="marzo") 		month=3;
		else if (m=="abril") 		month=4;
		else if (m=="mayo") 		month=5;
		else if (m=="junio") 		month=6;
		else if (m=="julio") 		month=7;
		else if (m=="agosto") 		month=8;
		else if (m=="septiembre") 	month=9;
		else if (m=="octubre") 		month=10;
		else if (m=="noviembre") 	month=11;
		else if (m=="diciembre") 	month=12;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(0);
		cal.set(year, month, day, 0, 0, 0);
		return cal.getTime(); 
	}
 
}
