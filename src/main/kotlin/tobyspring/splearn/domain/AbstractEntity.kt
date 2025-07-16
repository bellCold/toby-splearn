package tobyspring.splearn.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.proxy.HibernateProxy

@MappedSuperclass
abstract class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null) return false
        val oEffectiveClass = if (o is HibernateProxy) o.hibernateLazyInitializer.persistentClass else o.javaClass
        val thisEffectiveClass = if (this is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass else this.javaClass
        if (thisEffectiveClass != oEffectiveClass) return false
        val that = o as AbstractEntity
        return id != null && id == that.id
    }

    override fun hashCode(): Int {
        return if (this is HibernateProxy) (this as HibernateProxy).hibernateLazyInitializer.persistentClass.hashCode() else javaClass.hashCode()
    }
}