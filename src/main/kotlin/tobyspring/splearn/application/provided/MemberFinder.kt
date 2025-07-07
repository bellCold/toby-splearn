package tobyspring.splearn.application.provided

import tobyspring.splearn.domain.Member

interface MemberFinder {
    fun find(memberId: Long): Member
}