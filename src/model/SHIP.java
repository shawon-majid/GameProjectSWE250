package model;

public enum SHIP {
	BLUE("view/resources/shipchooser/playerShip3_blue.png"),
	GREEN("view/resources/shipchooser/playerShip3_green.png"),
	RED("view/resources/shipchooser/playerShip3_red.png"),
	ORANGE("view/resources/shipchooser/playerShip3_orange.png");
	
	private String urlShip;
	
	private SHIP(String urlShip) {
		this.urlShip = urlShip;
	}
	
	public String getUrl() {
		return this.urlShip;
	}
	
}
