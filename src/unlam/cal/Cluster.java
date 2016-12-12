package unlam.cal;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.util.Log;

public class Cluster {

	private ArrayList<String> comments 	= new ArrayList<String>();
	private ArrayList<Event> queue 		= new ArrayList<Event>();
	private String title 			= "";
	private int type 			= 0;
	private int id				= 0;

	public static Pattern CAMBIO_CARRERA 	= Pattern.compile(".*(Solicitud de cambio/simult|Pase de universidad).*");
	public static Pattern CUATRIMESTRE   	= Pattern.compile(".*(primer cuatrim|segundo cuatrim|Curso de Verano).*");
	public static Pattern FINALES		= Pattern.compile(".*(Ex.menes turno).*");
	public static Pattern CURSO_INGRESO	= Pattern.compile(".*(Curso de Ingreso).*");

	public static int TYPE_UNKNOWN 		= 0;
	public static int TYPE_CAMBIO_CARRERA 	= 1;
	public static int TYPE_CUATRIMESTRE	= 2;
	public static int TYPE_FINALES		= 3;
	public static int TYPE_CURSO_INGRESO	= 4;
	public static int TYPE_CUSTOM		= 5;

	public int getType() 			{ return type; }
	private void setType(int t) 		{ type = t; }

	public int getID() 			{ return id; }
	public void setID(int t) 		{ id = t; }

	public String getTitle() 		{ return title; }

	public void addComment(String c) 	{ comments.add(c); }
	public ArrayList<String> getComments() 	{ return comments; }
	public void setComments(ArrayList<String> a) { comments = a; }

	public void setEvents(ArrayList<Event> q) 	{ queue = q; }
	public ArrayList<Event> getEvents() 		{ return queue; }

	public void setTitle(String t) {
		title = t;
                if (CAMBIO_CARRERA.matcher(title).matches())
                	setType(TYPE_CAMBIO_CARRERA);
                else if (CUATRIMESTRE.matcher(title).matches())
                	setType(TYPE_CUATRIMESTRE);
                else if (FINALES.matcher(title).matches())
                	setType(TYPE_FINALES);
                else if (CURSO_INGRESO.matcher(title).matches())
                	setType(TYPE_CURSO_INGRESO);
                else
                	setType(TYPE_UNKNOWN);
	}

	public void addEvent(String title,String date) {
		Event ev = new Event();
		ev.setTitle(title);
		if (type==TYPE_CAMBIO_CARRERA) {
			if (Event.RECEPCION.matcher(title).matches()) 
				ev.setType(Event.PASE_DE_UNIVERSIDAD);
			else
				ev.setType(Event.CAMBIO_CARRERA);
		}
		else if (type==TYPE_CUATRIMESTRE) {
			if (Event.INI_CLASES.matcher(title).matches()) 
				ev.setType(Event.INI_DE_CLASES);
			else if (Event.VERIFICACION.matcher(title).matches()) 
				ev.setType(Event.VERIFICACION_INSCRIPCION);
			else if (Event.INI_INGRESANTES.matcher(title).matches()) 
				ev.setType(Event.INI_DE_CLASES_INGRESANTES);
			else if (Event.FIN_CLASES.matcher(title).matches()) 
				ev.setType(Event.FIN_DE_CLASES);
			else if (Event.MEDICINA.matcher(title).matches()) 
				ev.setType(Event.INSC_MEDICINA);
			else if (Event.FORM_CONTINUA.matcher(title).matches()) 
				ev.setType(Event.INSC_FORMACION_CONTINUA);
			else if (Event.HUMANIDADES.matcher(title).matches()) 
				ev.setType(Event.INSC_HUMANIDADES);
			else if (Event.DERECHO.matcher(title).matches()) 
				ev.setType(Event.INSC_DERECHO);
			else if (Event.SALUD.matcher(title).matches()) 
				ev.setType(Event.INSC_SALUD);
			else if (Event.ECONOMICAS.matcher(title).matches()) 
				ev.setType(Event.INSC_ECONOMICAS);
			else if (Event.INGENIERIA.matcher(title).matches()) 
				ev.setType(Event.INSC_INGENIERIA);
			else if (Event.EXTENSION.matcher(title).matches()) 
				ev.setType(Event.INSC_APM);
			else if (Event.TODOS.matcher(title).matches()) 
				ev.setType(Event.INSC_TODOS_LOS_DEPARTAMENTOS);
			else
				ev.setType(Event.UNKNOWN);
		}
		else if (type==TYPE_FINALES) {
			if (Event.INSC_FINALES.matcher(title).matches()) 
				ev.setType(Event.INCRIPCION_FINALES);
			else if (Event.VERIFICACION.matcher(title).matches()) 
				ev.setType(Event.VERIFICACION_FINALES);
			else if (Event.EXAMENES.matcher(title).matches()) 
				ev.setType(Event.FECHA_FINALES);
			else
				ev.setType(Event.UNKNOWN);
		}
		else if (type==TYPE_CURSO_INGRESO) {
			if (Event.INSCRIPCION.matcher(title).matches()) 
				ev.setType(Event.INSCRIPCION_CURSO_INGRESO);
			else if (Event.INI_C_INGRESO.matcher(title).matches()) 
				ev.setType(Event.INI_DE_CLASES_CURSO_INGRESO);
			else if (Event.FIN_C_INGRESO.matcher(title).matches()) 
				ev.setType(Event.FIN_DE_CLASES_CURSO_INGRESO);
			else if (Event.EXAMENES.matcher(title).matches()) 
				ev.setType(Event.EXAMENES_CURSO_DE_INGRESO);
			else
				ev.setType(Event.UNKNOWN);
		}
		if (ev.setDate(date)) 
			queue.add(ev);
		else
			addComment(title+" "+date);

	} 
 
} 
