package org.docksidestage.dockside.dbflute.whitebox.entity;

import java.io.Serializable;

import org.dbflute.cbean.result.ListResultBean;
import org.dbflute.cbean.result.PagingResultBean;
import org.dbflute.util.DfTypeUtil;
import org.docksidestage.dockside.dbflute.exbhv.MemberBhv;
import org.docksidestage.dockside.dbflute.exentity.Member;
import org.docksidestage.dockside.unit.UnitContainerTestCase;

/**
 * @author jflute
 * @since 1.1.0-sp3 (2015/05/16 Saturday)
 */
public class WxEntitySerializableTest extends UnitContainerTestCase {

    private MemberBhv memberBhv;

    public void test_serializable_basic() {
        // ## Arrange ##
        Member member = new Member();
        member.setMemberName("Stojkovic");
        member.setBirthdate(currentLocalDate());
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        byte[] binary = DfTypeUtil.toBinary(member);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(serializable);
        assertNotNull(serializable);
        assertEquals(member.toString(), serializable.toString());
    }

    public void test_serializable_selected() {
        // ## Arrange ##
        Member member = memberBhv.selectByPK(3).get();
        member.setMemberName("Stojkovic");
        member.setBirthdate(currentLocalDate());
        member.setMemberStatusCode_Formalized();

        // ## Act ##
        byte[] binary = DfTypeUtil.toBinary(member);
        Serializable serializable = DfTypeUtil.toSerializable(binary);

        // ## Assert ##
        log(serializable);
        assertNotNull(serializable);
        assertEquals(member.toString(), serializable.toString());
    }

    public void test_serializable_relation() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne();
        });
        memberBhv.loadPurchase(memberList, purchaseCB -> {
            purchaseCB.setupSelect_Product();
            purchaseCB.query().addOrderBy_MemberId_Asc();
        });
        assertHasAnyElement(memberList);
        StringBuilder sb = new StringBuilder();
        memberList.forEach(original -> {
            byte[] serialized = DfTypeUtil.toBinary(original);
            Member member = DfTypeUtil.toSerializable(serialized);
            assertFalse(member.getMemberStatus().isPresent());
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                markHere("exists");
            }).orElse(() -> {
                markHere("not exists");
            });
            sb.append(ln()).append(member.toStringWithRelation());
        });
        log(sb.toString());
        assertMarked("exists");
        assertMarked("not exists");
    }

    public void test_serializable_listResult() throws Exception {
        ListResultBean<Member> memberList = memberBhv.selectList(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne();
        });
        memberBhv.loadPurchase(memberList, purchaseCB -> {
            purchaseCB.setupSelect_Product();
            purchaseCB.query().addOrderBy_MemberId_Asc();
        });
        assertHasAnyElement(memberList);
        byte[] serialized = DfTypeUtil.toBinary(memberList);
        ListResultBean<Member> deserialized = DfTypeUtil.toSerializable(serialized);
        assertHasAnyElement(deserialized);
        StringBuilder sb = new StringBuilder();
        for (Member member : deserialized) {
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                markHere("exists");
            }).orElse(() -> {
                markHere("not exists");
            });
            sb.append(ln()).append(member.toStringWithRelation());
        }
        log(sb.toString());
        assertMarked("exists");
        assertMarked("not exists");
        assertEquals(memberList.size(), deserialized.size());
        assertEquals(memberList.getAllRecordCount(), deserialized.getAllRecordCount());
    }

    public void test_serializable_pagingResult() throws Exception {
        PagingResultBean<Member> page = memberBhv.selectPage(cb -> {
            cb.setupSelect_MemberWithdrawalAsOne();
            cb.paging(4, 2);
        });
        memberBhv.loadPurchase(page, purchaseCB -> {
            purchaseCB.setupSelect_Product();
            purchaseCB.query().addOrderBy_MemberId_Asc();
        });
        assertHasAnyElement(page);
        byte[] serialized = DfTypeUtil.toBinary(page);
        PagingResultBean<Member> deserialized = DfTypeUtil.toSerializable(serialized);
        assertHasAnyElement(deserialized);
        StringBuilder sb = new StringBuilder();
        for (Member member : deserialized) {
            assertFalse(member.getMemberStatus().isPresent());
            member.getMemberWithdrawalAsOne().ifPresent(withdrawal -> {
                markHere("exists");
            }).orElse(() -> {
                markHere("not exists");
            });
            sb.append(ln()).append(member.toStringWithRelation());
        }
        log(sb.toString());
        assertMarked("exists");
        assertMarked("not exists");
        assertEquals(page.size(), deserialized.size());
        assertEquals(page.getAllRecordCount(), deserialized.getAllRecordCount());
        assertEquals(page.getAllPageCount(), deserialized.getAllPageCount());
        assertEquals(page.getPageSize(), deserialized.getPageSize());
        assertEquals(page.getCurrentPageNumber(), deserialized.getCurrentPageNumber());
    }
}
