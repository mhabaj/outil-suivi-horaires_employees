package centralApplication;

public class PersonalCard {

	private int id_Card;
	private Worker card_Owner;
	private int card_Owner_id;


	/**
	 * @param id_Card
	 * @param card_owner
	 */
	public PersonalCard(int id_Card, Worker card_owner) {
		this.setId_Card(id_Card);
		this.setCard_Owner(card_owner);
	}

	public int getId_Card() {
		return id_Card;
	}

	public void setId_Card(int id_Card) {
		this.id_Card = id_Card;
	}

	public Worker getCard_Owner() {
		return card_Owner;
	}

	public void setCard_Owner(Worker card_Owner) {
		this.card_Owner = card_Owner;
	}
}
