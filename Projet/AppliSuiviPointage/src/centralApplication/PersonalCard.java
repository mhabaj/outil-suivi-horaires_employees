package centralApplication;

public class PersonalCard {

	private int id_Card;
	private int card_Owner_id;

	/**
	 * @param id_Card
	 * @param card_Owner
	 * @param card_Owner_id
	 */
	public PersonalCard(int id_Card, int card_Owner_id) {
		this.id_Card = id_Card;
		this.card_Owner_id = card_Owner_id;
	}

	public int getId_Card() {
		return id_Card;
	}

	public void setId_Card(int id_Card) {
		this.id_Card = id_Card;
	}

	public int getCard_Owner_id() {
		return card_Owner_id;
	}

	public void setCard_Owner_id(int card_Owner_id) {
		this.card_Owner_id = card_Owner_id;
	}
}
