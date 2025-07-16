package tobyspring.splearn.application.member.provided

import tobyspring.splearn.domain.member.Member

interface MemberFinder {
    fun find(memberId: Long): Member
}