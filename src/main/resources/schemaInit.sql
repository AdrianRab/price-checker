--Drop table if exists room_details;
--
--create table room_details(
--    id INT AUTO_INCREMENT  PRIMARY KEY,
--    airportName VARCHAR(250),
--    departureDate VARCHAR(250),
--    returnDate VARCHAR(250),
--    duration INT,
--    roomName VARCHAR(250),
--    roomCode VARCHAR(250),
--    int price,
--    int discountPrice
--    offerCode VARCHAR(250),
--    receivedOn Date,
--    details VARCHAR(250),
--    originalPrice int
--)
--
--
--
--    private String airportName;
--
--    private String departureDate;
--
--    private String returnDate;
--
--    private int duration;
--    @Getter @Setter
--    private String roomName;
--    @Getter @Setter
--    private String roomCode;
--    @Getter @Setter
--    private int price;
--    @Getter @Setter
--    private int discountPrice;
--    @Getter @Setter
--    private String offerCode;
--    private Date receivedOn;
--    @Getter @Setter
--    private String details;
--    @Getter @Setter
--    private int originalPrice;
--    @Getter @Setter
--    @ElementCollection
--    @CollectionTable(name="emails", joinColumns = @JoinColumn(name="id"))
--    private List<String> emails = new ArrayList<>();