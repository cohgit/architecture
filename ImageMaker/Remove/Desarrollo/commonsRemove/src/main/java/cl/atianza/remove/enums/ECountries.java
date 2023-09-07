package cl.atianza.remove.enums;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Enum with default countries info.
 * @author pilin
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonSerialize(using = CountriesSerializer.class)
public enum ECountries {
	AFGHANISTAN("Afghanistan", "af", "com.af"),
	ALBANIA("Albania", "al", "al"),
	ALGERIA("Algeria", "dz", "dz"),
	AMERICAN_SAMOA("American Samoa", "as", "as"),
	ANDORRA("Andorra", "ad", "ad"),
	ANGOLA("Angola", "ao", "co.ao"),
	ANGUILLA("Anguilla", "ai", "com.ai"),
	ANTARTICA("Antarctica", "aq"),
	ANTIGUA_AND_BARBUDA("Antigua and Barbuda", "ag", "com.ag"),
	ARGENTINA("Argentina", "ar", "com.ar"),
	ARMENIA("Armenia", "am", "am"),
	ARUBA("Aruba", "aw"),
	AUSTRALIA("Australia", "au", "com.au"),
	AUSTRIA("Austria", "at", "at"),
	AZERBAIJAN("Azerbaijan", "az", "az"),
	BAHAMAS("Bahamas", "bs", "bs"),
	BAHRAIN("Bahrain", "bh", "com.bh"),
	BANGLADESH("Bangladesh", "bd", "com.bd"),
	BARBADOS("Barbados", "bb"),
	BELARUS("Belarus", "by", "by"),
	BELGIUM("Belgium", "be", "be"),
	BELIZE("Belize", "bz", "com.bz"),
	BENIN("Benin", "bj", "bj"),
	BERMUDA("Bermuda", "bm"),
	BHUTAN("Bhutan", "bt", "bt"),
	BOLIVIA("Bolivia", "bo", "com.bo"),
	BOSNIA_AND_HERZEGOVINA("Bosnia and Herzegovina", "ba", "ba"),
	BOTSWANA("Botswana", "bw", "co.bw"),
	BOUVET_ISLAND("Bouvet Island", "bv"),
	BRAZIL("Brazil", "br", "com.br"),
	BRITISH_INDIAN_OCEAN_TERRITORY("British Indian Ocean Territory", "io", "io"),
	BRUNEI_DARUSSALAM("Brunei Darussalam", "bn", "com.bn"),
	BULGARIA("Bulgaria", "bg", "bg"),
	BURKINA_FASO("Burkina Faso", "bf", "bf"),
	BURUNDI("Burundi", "bi", "bi"),
	CAMBODIA("Cambodia", "kh", "com.kh"),
	CAMEROON("Cameroon", "cm", "cm"),
	CANADA("Canada", "ca", "ca"),
	CAPE_VERDE("Cape Verde", "cv", "cv"),
	CAYMAN_ISLANDS("Cayman Islands", "ky"),
	CENTRAL_AFRICAN_REPUBLIC("Central African Republic", "cf", "cf"),
	CHAD("Chad", "td", "td"),
	CHILE("Chile", "cl", "cl"),
	CHINA("China", "cn", "cn"),
	CHRISTMAS_ISLAND("Christmas Island", "cx", "cx"),
	COCOS_ISLANDS("Cocos (Keeling) Islands", "cc", "cc"),
	COLOMBIA("Colombia", "co", "com.co"),
	COMOROS("Comoros", "km"),
	CONGO("Congo", "cg"),
	CONGO_THE_DEMOCRATIC_REPUBLIC_OF_THE("Congo, the Democratic Republic of the", "cd", "cd"),
	COOK_ISLANDS("Cook Islands", "ck", "co.ck"),
	COSTA_RICA("Costa Rica", "cr", "co.cr"),
	CORTE_D_IVOIRE("Cote D'ivoire", "ci", "ci"),
	CROATIA("Croatia", "hr", "hr"),
	CUBA("Cuba", "cu", "com.cu"),
	CYPRUS("Cyprus", "cy", "com.cy"),
	CZECH_REPUBLIC("Czech Republic", "cz", "cz"),
	DENMARK("Denmark", "dk", "dk"),
	DJIBOUTI("Djibouti", "dj", "dj"),
	DOMINICA("Dominica", "dm", "dm"),
	DOMINICAN_REPUBLIC("Dominican Republic", "do", "com.do"),
	ECUADOR("Ecuador", "ec", "com.ec"),
	EGYPT("Egypt", "eg", "com.eg"),
	EL_SALVADOR("El Salvador", "sv", "com.sv"),
	EQUATORIAL_GUINEA("Equatorial Guinea", "gq"),
	ERITREA("Eritrea", "er"),
	ESTONIA("Estonia", "ee", "ee"),
	ETHIOPIA("Ethiopia", "et", "com.et"),
	FALKLAND_ISLANDS_MALVINAS("Falkland Islands (Malvinas)", "fk"),
	FAROE_ISLANDS("Faroe Islands", "fo"),
	FIJI("Fiji", "fj", "com.fj"),
	FINLAND("Finland", "fi", "fi"),
	FRANCE("France", "fr", "fr"),
	FRENCH_GUIANA("French Guiana", "gf", "gf"),
	FRENCH_POLYNESIA("French Polynesia", "pf"),
	FRENCH_SOUTHERN_TERRITORIES("French Southern Territories", "tf"),
	GABON("Gabon", "ga", "ga"),
	GAMBIA("Gambia", "gm", "gm"),
	GEORGIA("Georgia", "ge", "ge"),
	GERMANY("Germany", "de", "de"),
	GHANA("Ghana", "gh", "com.gh"),
	GIBRALTAR("Gibraltar", "gi", "com.gi"),
	GREECE("Greece", "gr", "gr"),
	GREENLAND("Greenland", "gl", "gl"),
	GRENADA("Grenada", "gd"),
	GUADELOUPE("Guadeloupe", "gp", "gp"),
	GUAM("Guam", "gu"),
	GUATEMALA("Guatemala", "gt", "com.gt"),
	GUINEA("Guinea", "gn"),
	GUINEA_BISSAU("Guinea-Bissau", "gw"),
	GUYANA("Guyana", "gy", "gy"),
	HAITI("Haiti", "ht", "ht"),
	HEARD_ISLAND_AND_MCDONALD_ISLANDS("Heard Island and Mcdonald Islands", "hm"),
	HOLY_SEE_VATICAN("Holy See (Vatican City State)", "va"),
	HONDURAS("Honduras", "hn", "hn"),
	HONG_KONG("Hong Kong", "hk", "com.hk"),
	HUNGARY("Hungary", "hu", "hu"),
	ICELAND("Iceland", "is", "is"),
	INDIA("India", "in", "co.in"),
	INDONESIA("Indonesia", "id", "co.id"),
	IRAN_ISLAMIC_REPUBLIC_OF("Iran, Islamic Republic of", "ir"),
	IRAQ("Iraq", "iq", "iq"),
	IRELAND("Ireland", "ie", "ie"),
	ISRAEL("Israel", "il", "co.il"),
	ITALY("Italy", "it", "it"),
	JAMAICA("Jamaica", "jm", "com.jm"),
	JAPAN("Japan", "jp", "co.jp"),
	JORDAN("Jordan", "jo", "jo"),
	KAZAKHSTAN("Kazakhstan", "kz", "kz"),
	KENYA("Kenya", "ke", "co.ke"),
	KIRIBATI("Kiribati", "ki", "ki"),
	KOREA_DEMOCRATIC_PEOPLES_REPUBLIC_OF("Korea, Democratic People's Republic of", "kp"),
	KOREA_REPUBLIC_OF("Korea, Republic of", "kr", "co.kr"),
	KUWAIT("Kuwait", "kw", "com.kw"),
	KYRGYZSTAN("Kyrgyzstan", "kg", "kg"),
	LAO_PEOPLES_DEMOCRATIC_REPUBLIC("Lao People's Democratic Republic", "la", "la"),
	LATVIA("Latvia", "lv", "lv"),
	LEBANON("Lebanon", "lb", "com.lb"),
	LESOTHO("Lesotho", "ls", "co.ls"),
	LIBERIA("Liberia", "lr"),
	LIBYAN_ARAB_JAMAHIRIYA("Libyan Arab Jamahiriya", "ly", "com.ly"),
	LIECHSTENSTEIN("Liechtenstein", "li", "li"),
	LITHUANIA("Lithuania", "lt", "lt"),
	LUXEMBOURG("Luxembourg", "lu", "lu"),
	MACAO("Macao", "mo"),
	MACEDONIA_THE_FORMER_YUGOSALV_REPUBLIC_OF("Macedonia, the Former Yugosalv Republic of", "mk", "mk"),
	MADAGASCAR("Madagascar", "mg", "mg"),
	MALAWI("Malawi", "mw", "mw"),
	MALAYSIA("Malaysia", "my", "com.my"),
	MALDIVES("Maldives", "mv", "mv"),
	MALI("Mali", "ml", "ml"),
	MALTA("Malta", "mt", "com.mt"),
	MARSHALL_ISLANDS("Marshall Islands", "mh"),
	MARTINIQUE("Martinique", "mq"),
	MAURITANIA("Mauritania", "mr"),
	MAURITIUS("Mauritius", "mu", "mu"),
	MAYOTTE("Mayotte", "yt"),
	MEXICO("Mexico", "mx", "com.mx"),
	MICRONESIA("Micronesia, Federated States of", "fm", "fm"),
	MOLDOVA("Moldova, Republic of", "md", "md"),
	MONACO("Monaco", "mc"),
	MONGOLIA("Mongolia", "mn", "mn"),
	MONTSERRAT("Montserrat", "ms", "ms"),
	MOROCCO("Morocco", "ma", "co.ma"),
	MOZAMBIQUE("Mozambique", "mz", "co.mz"),
	MYANMAR("Myanmar", "mm", "com.mm"),
	NAMIBIA("Namibia", "na", "com.na"),
	NAURU("Nauru", "nr", "nr"),
	NEPAL("Nepal", "np", "com.np"),
	NETHERLANDS("Netherlands", "nl", "nl"),
	NETHERLANDS_ANTILLES("Netherlands Antilles", "an"),
	NEW_CALEDONIA("New Caledonia", "nc"),
	NEW_ZEALAND("New Zealand", "nz", "co.nz"),
	NICARAGUA("Nicaragua", "ni", "com.ni"),
	NIGER("Niger", "ne", "ne"),
	NIGERIA("Nigeria", "ng", "com.ng"),
	NIUE("Niue", "nu", "nu"),
	NORFOLK_ISLAND("Norfolk Island", "nf"),
	NORTHERN_MARIANA_ISLANDS("Northern Mariana Islands", "mp"),
	NORWAY("Norway", "no", "no"),
	OMAN("Oman", "om", "com.om"),
	PAKISTAN("Pakistan", "pk", "com.pk"),
	PALAU("Palau", "pw"),
	PALESTINIAN_TERRITORY_OCCUPIED("Palestinian Territory, Occupied", "ps", "ps"),
	PANAMA("Panama", "pa", "com.pa"),
	PAPUA_NEW_GUINEA("Papua New Guinea", "pg", "com.pg"),
	PARAGUAY("Paraguay", "py", "com.py"),
	PERU("Peru", "pe", "com.pe"),
	PHILIPPINES("Philippines", "ph", "com.ph"),
	PITCAIRN("Pitcairn", "pn"),
	POLAND("Poland", "pl", "pl"),
	PORTUGAL("Portugal", "pt", "pt"),
	PUERTO_RICO("Puerto Rico", "pr", "com.pr"),
	QATAR("Qatar", "qa", "com.qa"),
	REUNION("Reunion", "re"),
	ROMANIA("Romania", "ro", "ro"),
	RUSSIAN_FEDERATION("Russian Federation", "ru", "ru"),
	RWANDA("Rwanda", "rw", "rw"),
	SAINT_HELENA("Saint Helena", "sh", "sh"),
	SAINT_KITTS_AND_NEVIS("Saint Kitts and Nevis", "kn"),
	SAINT_LUCIA("Saint Lucia", "lc"),
	SAINT_PIERRE_AND_MIQUELON("Saint Pierre and Miquelon", "pm"),
	SAINT_VINCENT_AND_THE_GRENADINES("Saint Vincent and the Grenadines", "vc", "com.vc"),
	SAMOA("Samoa", "ws", "ws"),
	SAN_MARINO("San Marino", "sm", "sm"),
	SAO_TOME_AND_PRINCIPE("Sao Tome and Principe", "st"),
	SAUDI_ARABIA("Saudi Arabia", "sa", "com.sa"),
	SENEGAL("Senegal", "sn", "sn"),
	SERBIA_AND_MONTENEGRO("Serbia and Montenegro", "rs", "rs"),
	SEYCHELLES("Seychelles", "sc", "sc"),
	SIERRA_LEONE("Sierra Leone", "sl", "com.sl"),
	SINGAPORE("Singapore", "sg", "com.sg"),
	SLOVAKIA("Slovakia", "sk", "sk"),
	SLOVENIA("Slovenia", "si", "si"),
	SOLOMON_ISLANDS("Solomon Islands", "sb", "com.sb"),
	SOMALIA("Somalia", "so", "so"),
	SOUTH_AFRICA("South Africa", "za", "co.za"),
	SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS("South Georgia and the South Sandwich Islands", "gs"),
	SPAIN("Spain", "es", "es"),
	SRI_LANKA("Sri Lanka", "lk", "lk"),
	SUDAN("Sudan", "sd"),
	SURINAME("Suriname", "sr", "sr"),
	SVALBARD_AND_JAN_MAYEN("Svalbard and Jan Mayen", "sj"),
	SWAZILAND("Swaziland", "sz"),
	SWEDEN("Sweden", "se", "se"),
	SWITZERLAND("Switzerland", "ch", "ch"),
	SYRIAN_ARAB_REPUBLIC("Syrian Arab Republic", "sy"),
	TAIWAN("Taiwan, Province of China", "tw", "com.tw"),
	TAJIKISTAN("Tajikistan", "tj", "com.tj"),
	TANZANIA_UNITED_REPUBLIC_OF("Tanzania, United Republic of", "tz", "co.tz"),
	THAILAND("Thailand", "th", "co.th"),
	TIMOR_LESTE("Timor-Leste", "tl", "tl"),
	TOGO("Togo", "tg", "tg"),
	TOKELAU("Tokelau", "tk", "tk"),
	TONGA("Tonga", "to", "to"),
	TRINIDAD_AND_TOBAGO("Trinidad and Tobago", "tt", "tt"),
	TUNISIA("Tunisia", "tn", "tn"),
	TURKEY("Turkey", "tr", "com.tr"),
	TURKMENISTAN("Turkmenistan", "tm", "tm"),
	TURKS_AND_CAICOS_ISLANDS("Turks and Caicos Islands", "tc"),
	TUVALU("Tuvalu", "tv"),
	UGANDA("Uganda", "ug", "co.ug"),
	UKRAINE("Ukraine", "ua", "com.ua"),
	UNITED_ARAB_EMIRATES("United Arab Emirates", "ae"),
	UNITED_KINGDOM("United Kingdom", "uk", "co.uk"),
	UNITED_STATES("United States", "us", "com"),
	UNITED_STATES_MINOR_OUTLYING_ISLANDS("United States Minor Outlying Islands", "um"),
	URUGUAY("Uruguay", "uy", "com.uy"),
	UZBEKISTAN("Uzbekistan", "uz", "co.uz"),
	VANUATU("Vanuatu", "vu", "vu"),
	VENEZUELA("Venezuela", "ve", "co.ve"),
	VIET_NAM("Viet Nam", "vn", "com.vn"),
	VIRGIN_ISLANDS_BRITISH("Virgin Islands, British", "vg", "vg"),
	VIRGIN_ISLANDS_US("Virgin Islands, U.S.", "vi", "co.vi"),
	WALLIS_AND_FUTUNA("Wallis and Futuna", "wf"),
	WESTERN_SAHARA("Western Sahara", "eh"),
	YEMEN("Yemen", "ye"),
	ZAMBIA("Zambia", "zm", "co.zm"),
	ZIMBABWE("Zimbabwe", "zw", "co.zw")
	;
	
	private String country;
	private String code;
	private String domain;
	private boolean active = true;
	
	private ECountries(String country, String code, String domain) {
		this.country = country;
		this.code = code;
		this.domain = domain;
	}
	

	private ECountries(String country, String code, String domain, boolean active) {
		this.country = country;
		this.code = code;
		this.domain = domain;
		this.active = active;
	}

	private ECountries(String country, String code) {
		this.country = country;
		this.code = code;
	}

	private ECountries(String country, String code, boolean active) {
		this.country = country;
		this.code = code;
		this.active = active;
	}

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCollectionName() {
		return "country"+this.code.toUpperCase();
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getFullDomain() {
		return domain != null ? "google." + domain : null;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@JsonCreator
    static ECountries findValue(@JsonProperty("code") String code) {
        return obtain(code);
    }
	
	public static ECountries obtain(String code) {
		for(ECountries c : ECountries.values()) {
			if (c.getCode().equalsIgnoreCase(code)) return c;
		}
		
		return null;
	}
	
	@JsonCreator
    static String findCountries(@JsonProperty("country") String country) {
        return obtainCountries(country);
    }
	
	public static String obtainCountries(String country) {
		for(ECountries c : ECountries.values()) {
			if (c.getCountry().equalsIgnoreCase(country)) return c.getCode();
		}
		
		return null;
	}
}

class CountriesSerializer extends StdSerializer<ECountries> {
	private static final long serialVersionUID = -8761216512740416212L;

	public CountriesSerializer() {
		super(ECountries.class);
	}
	
	protected CountriesSerializer(Class<ECountries> t) {
		super(t);
	}

	@Override
	public void serialize(ECountries value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeStartObject();
        generator.writeFieldName(value.name());
        generator.writeBoolean(true);
        generator.writeFieldName("country");
        generator.writeString(value.getCountry());
        generator.writeFieldName("code");
        generator.writeString(value.getCode());
        generator.writeFieldName("collectionName");
        generator.writeString(value.getCollectionName());
        generator.writeFieldName("domain");
        generator.writeString(value.getDomain());
        generator.writeFieldName("fullDomain");
        generator.writeString(value.getFullDomain());
        generator.writeEndObject();
	}
}
