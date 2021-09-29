package com.project.hss.api.v1.dto.response.api;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class Response {

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

                private String bsnsMbyNm;
                private String houseDtlSecdNm;
                private String houseManageNo;
                private String houseNm;
                private String pblancNo;
                private String przwnerPresnatnDe;
                private String rceptBgnde;
                private String rceptEndde;
                private String rcritPblancDe;
                private String rentSecdNm;
                private String sido;
            }
        }
    }
}
