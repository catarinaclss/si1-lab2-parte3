package controllers;

import java.util.Collections;
import java.util.List;

import models.Evento;
import models.Participante;
import models.Tema;
import models.DAO.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class Application extends Controller {

	public static GenericDAO dao = new GenericDAO();

	@Transactional
	public static Result index() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		Collections.sort(eventos);
		return ok(index.render(eventos));
	}
	
	public static Result abrirCadastroDeEvento() {
		Form<Evento> evento = Form.form(Evento.class);
		return ok(eventos.render(evento));
	}
	
	@Transactional
	public static Result cadastrarEvento(){
		Form<Evento> form = Form.form(Evento.class).bindFromRequest();

		if (form.hasErrors())

			return badRequest(eventos.render(form));

		Evento evento = form.get();

		dao.persist(evento);
		dao.flush();

		return redirect(routes.Application.index());
	}
	
	@Transactional
	public static Result inscricao() {
		DynamicForm requestData = Form.form().bindFromRequest();

		long id_evento = Long.parseLong(requestData.get("ID_EVENTO"));
		Evento evento = dao.findByEntityId(Evento.class, id_evento);
		
		Form<Participante> formParticipante = Form.form(Participante.class);
		return ok(inscricao.render(evento, formParticipante));
	}

	@Transactional
	public static Result inscrever(Long id) {

		Form<Participante> alterarForm = Form.form(Participante.class).bindFromRequest();
		Participante participante = alterarForm.get();
		Evento evento = dao.findByEntityId(Evento.class, id);
		
		if (alterarForm.hasErrors()) {
			return badRequest(inscricao.render(evento, alterarForm));
		}
		evento.getParticipantes().add(participante);

		dao.merge(evento);
		dao.flush();
		return redirect(routes.Application.index());
	}
	
	@Transactional
	public static Result listarParticipantes() {
		DynamicForm requestData = Form.form().bindFromRequest();

		long ID = Long.parseLong(requestData.get("ID"));
		Evento evento = dao.findByEntityId(Evento.class, ID);
		
		
		return ok(listaParticipantes.render(ID, evento));
	}
	
	@Transactional
	public static Result informacoes() {
		DynamicForm requestData = Form.form().bindFromRequest();

		long ID = Long.parseLong(requestData.get("ID"));
		Evento evento = dao.findByEntityId(Evento.class, ID);
		
		return ok(informacoes.render(ID, evento));
	}
	
	@Transactional
	public static Result cadastrarTemas() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		return ok(temas.render(eventos));
	}
	
	@Transactional
	public static Result novoTema(Long id, String tema) {
		
		Tema novoTema = new Tema();
		novoTema.setNome(tema);
		Evento evento = dao.findByEntityId(Evento.class, id);
		evento.getTemas().add(novoTema);
		dao.persist(evento);
		dao.merge(evento);
		dao.flush();
		return redirect(routes.Application.index());
	}
	
	
}
