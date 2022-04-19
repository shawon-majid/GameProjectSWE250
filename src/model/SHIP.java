package model;

public enum SHIP {
	BLUE("view/resources/shipchooser/playerShip3_blue.png", "view/resources/shipchooser/playerLife3_blue.png"),
	GREEN("view/resources/shipchooser/playerShip3_green.png", "view/resources/shipchooser/playerLife3_green.png"),
	RED("view/resources/shipchooser/playerShip3_red.png", "view/resources/shipchooser/playerLife3_red.png"),
	ORANGE("view/resources/shipchooser/playerShip3_orange.png", "view/resources/shipchooser/playerLife3_orange.png");
	
	private String urlShip;
	private String urlShipLife;
	
	private SHIP(String urlShip, String urlShipLife) {
		this.urlShip = urlShip;
		this.urlShipLife = urlShipLife;
	}
	
	public String getUrl() {
		return this.urlShip;
	}
	
	public String getUrlLife() {
		return urlShipLife;
	}
	
}
