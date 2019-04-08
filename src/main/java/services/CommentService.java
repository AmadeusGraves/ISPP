
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Actor;
import domain.Comment;
import domain.Driver;
import domain.Passenger;
import domain.Route;
import forms.CommentForm;

@Service
@Transactional
public class CommentService {

	//managed repository
	@Autowired
	private CommentRepository	commentRepository;

	//Supporting Services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private PassengerService	passengerService;


	//CRUD 
	public Comment create() {
		Comment result;

		result = new Comment();

		return result;
	}

	public Comment findOne(int commentId) {
		Assert.isTrue(this.commentRepository.exists(commentId));

		Comment result;

		result = this.commentRepository.findOne(commentId);

		return result;

	}

	public Collection<Comment> findAll() {
		Collection<Comment> result;

		result = this.commentRepository.findAll();

		return result;
	}

	public Comment save(Comment comment) {
		this.validateComment(comment);

		Comment result;

		result = this.save(comment);

		return result;
	}

	public void validateComment(Comment comment) {
		Assert.notNull(comment);
		Driver driver;
		Collection<Passenger> passengers;

		// El driver y el passenger se deben corresponder con los de la ruta que aparezca en el comment
		driver = comment.getRoute().getDriver();
		passengers = this.passengerService.findPassengersAcceptedByRoute(comment.getRoute().getId());

		Assert.isTrue(driver.getId() == comment.getDriver().getId());
		Assert.isTrue(passengers.contains(comment.getPassenger()));

		if (comment.getFromDriver()) {
			// Es un comentario que escribe el conductor de la ruta a un passenger de la misma
			Assert.isTrue(this.actorService.findByPrincipal().getId() == comment.getDriver().getId());
		} else {
			// Es un comentario que escribe un passenger de la ruta a un driver de la misma
			Assert.isTrue(passengers.contains(this.actorService.findByPrincipal()));
		}

		// TODO: terminar
	}

	public Collection<Comment> findCommentsMadeByPassenger(int passengerId) {
		Collection<Comment> result;

		result = this.commentRepository.findCommentsMadeByPassenger(passengerId);

		return result;
	}

	public Collection<Comment> findCommentsMadeByDriver(int driverId) {
		Collection<Comment> result;

		result = this.commentRepository.findCommentsMadeByDriver(driverId);

		return result;
	}

	public Collection<Comment> findCommentsMadeToPassenger(int passengerId) {
		Collection<Comment> result;

		result = this.commentRepository.findCommentsMadeToPassenger(passengerId);

		return result;
	}

	public Collection<Comment> findCommentsMadeToDriver(int driverId) {
		Collection<Comment> result;

		result = this.commentRepository.findCommentsMadeToDriver(driverId);

		return result;
	}

	public Double avgStarsFromDriver(int driverId) {
		Double result;

		result = this.commentRepository.avgStarsFromDriver(driverId);

		return result;
	}

	public Double avgStarsFromPassenger(int passengerId) {
		Double result;

		result = this.commentRepository.avgStarsFromPassenger(passengerId);

		return result;
	}

	public Comment findCommentFromDriver(int routeId, int passengerId, int driverId) {
		Comment result;

		result = this.commentRepository.findCommentFromDriver(routeId, passengerId, driverId);

		return result;
	}

	public Comment findCommentFromPassenger(int routeId, int passengerId, int driverId) {
		Comment result;

		result = this.commentRepository.findCommentFromPassenger(routeId, passengerId, driverId);

		return result;
	}

	public boolean canComment(Actor actor, Route route) {
		boolean result = false;
		Collection<Passenger> passengers;

		// Primero comprobamos si estamos en el rango de tiempo en el que se permitir�a comentar para esta ruta:
		// Es decir, la ruta ha comenzado y ha pasado el tiempo estimado de la misma. Adem�s no han pasado 24 horas desde ese momento.

		Calendar now = Calendar.getInstance();
		now.setTime(new Date());

		Calendar routeCommentPeriodStart = Calendar.getInstance();
		routeCommentPeriodStart.setTime(route.getDepartureDate());
		routeCommentPeriodStart.add(Calendar.MINUTE, route.getEstimatedDuration());

		if (now.before(routeCommentPeriodStart)) {
			return result;
		}

		// Reutilizamos esta variable y ahora ser�a el fin del periodo:
		routeCommentPeriodStart.add(Calendar.HOUR, 24);

		if (now.after(routeCommentPeriodStart)) {
			return result;
		}

		// Si se llega hasta aqu�, le est� permitido comentar, faltaria ver si es alguien de los passengers con reservations aceptadas o el driver
		if (actor instanceof Driver) {
			if (route.getDriver().getId() == actor.getId()) {
				result = true;
			}
		} else {

			passengers = this.passengerService.findPassengersAcceptedByRoute(route.getId());
			if (passengers.contains(actor)) {
				// En caso de ser un pasenger, adem�s comprobaremos si ya ha comentado o no a este driver para esta ruta
				if (this.findCommentFromPassenger(route.getId(), actor.getId(), route.getDriver().getId()) == null) {
					result = true;
				}
			}
		}

		return result;
	}

	public Collection<Passenger> passengersToComment(Driver driver, Route route) {
		Collection<Passenger> aux, result;
		result = new ArrayList<Passenger>();

		aux = this.passengerService.findPassengersAcceptedByRoute(route.getId());

		for (Passenger passenger : aux) {

			if (this.findCommentFromDriver(route.getId(), passenger.getId(), driver.getId()) == null) {
				result.add(passenger);
			}

		}

		return result;
	}

	public Comment reconstruct(CommentForm commentForm) {
		Assert.notNull(commentForm);

		Comment result;

		result = new Comment();

		result.setText(commentForm.getText());
		result.setStar(commentForm.getStar());

		return result;

	}
}
