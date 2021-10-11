package com.project.hss.api.v1.dto.response.api;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class APTLttotPblancMdlRes {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

    @Getter
    @Setter
    @XmlRootElement(name = "header")
    private static class Header {

        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @XmlRootElement(name = "body")
    private static class Body {

        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;

        @Getter
        @Setter
        @XmlRootElement(name = "items")
        private static class Items {

            private List<Item> item;

            @Getter
            @Setter
            @XmlRootElement(name = "item")
            public static class Item {

                private String etchshldco;
                private String housemanageno;
                private String housety;
                private String insttrecomendhshldco;
                private String lfefrsthshldco;
                private String lttottopamount;
                private String mnychhshldco;
                private String modelno;
                private String nwwdshshldco;
                private String oldparentssuporthshldco;
                private String pblancno;
                private String spsplyhshldco;
                private String suplyar;
                private String suplyhshldco;
                private String transrinsttenfsnhshldco;
            }
        }
    }
}
