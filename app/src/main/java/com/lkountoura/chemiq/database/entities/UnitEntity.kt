import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "units")
data class UnitEntity(
    @PrimaryKey
    val uuid: String,
    val id: Int,
    val title: String,
    val grade: String,
    val sortOrder: Int,
) {
    companion object {
        fun build(
            id: Int,
            title: String,
            grade: String,
            sortOrder: Int,
        ): UnitEntity = UnitEntity(
            uuid = UUID.nameUUIDFromBytes("$id:$title".toByteArray()).toString(),
            id = id,
            title = title,
            grade = grade,
            sortOrder = sortOrder,
        )
    }
}