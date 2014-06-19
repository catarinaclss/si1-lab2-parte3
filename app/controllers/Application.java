package controllers;

import java.util.Collections;
import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import models.Evento;
import models.Tema;
import models.Participante;
import models.DAO.GenericDAO;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;

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

		long id = Long.parseLong(requestData.get("ID"));

		Form<Participante> participante = Form.form(Participante.class);

		return ok(inscricao.render(id, participante));
	}

	@Transactional
	public static Result inscrever(Long id) {

		Form<Participante> alterarForm = Form.form(Participante.class)
				.bindFromRequest();
		if (alterarForm.hasErrors()) {
			return badRequest(inscricao.render(id, alterarForm));
		}
		Participante participante = alterarForm.get();
		Evento evento = dao.findByEntityId(Evento.class, id);

		evento.getParticipantes().add(participante);

		dao.merge(evento);
		dao.flush();
		return redirect(routes.Application.index());
	}
	
	@Transactional
	public static Result listarParticipantes() {
		List<Evento> eventos = dao.findAllByClassName("Evento");
		return ok(listaParticipantes.render(eventos));
	}
	
	@Transactional
	public static Result informacoes() {
		DynamicForm requestData = Form.form().bindFromRequest();

		long ID = Long.parseLong(requestData.get("ID"));
		Evento evento = dao.findByEntityId(Evento.class, ID);
		
		return ok(informacoes.render(ID, evento));
	}
	
	
	
}
