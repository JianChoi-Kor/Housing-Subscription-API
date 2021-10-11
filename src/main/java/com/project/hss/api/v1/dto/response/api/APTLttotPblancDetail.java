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
public class APTLttotPblancDetail {

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

                private String cntrctcnclsbgnde;
                private String cntrctcnclsendde;
                private String gnrlrnk1crsparearceptpd;
                private String gnrlrnk1etcarearcptdepd;
                private String gnrlrnk1etcggrcptdepd;
                private String gnrlrnk2crsparearceptpd;
                private String gnrlrnk2etcarearcptdepd;
                private String gnrlrnk2etcggrcptdepd;
                private String hmpgadres;
                private String housemanageno;
                private String housenm;
                private String hssplyadres;
                private String pblancno;
                private String przwnerpresnatnde;
                private String rcritpblancde;
                private String spsplyrceptbgnde;
                private String spsplyrceptendde;
                private String totsuplyhshldco;
            }
        }
    }
}
