package scorex

import scorex.core.network.message.BasicMsgDataTypes.InvData
import scorex.crypto.encode.BytesEncoder
import supertagged.TaggedType

package object core {

  //TODO implement ModifierTypeId as a trait
  object ModifierTypeId extends TaggedType[Byte]

  object ModifierId extends TaggedType[Array[Byte]]

  object VersionTag extends TaggedType[Array[Byte]]

  type ModifierTypeId = ModifierTypeId.Type

  type ModifierId = ModifierId.Type

  type VersionTag = VersionTag.Type

  def idsToString(ids: Seq[(ModifierTypeId, ModifierId)])
                 (implicit encoder: BytesEncoder): String = (ids.headOption, ids.lastOption) match {
    case (Some(f), Some(l)) if f._2 sameElements l._2 => s"[(${f._1},${encoder.encode(f._2)})]"
    case (Some(f), Some(l)) => s"[(${f._1},${encoder.encode(f._2)})..(${l._1},${encoder.encode(l._2)})]"
    case _ => "[]"
  }

  def idsToString(modifierType: ModifierTypeId, ids: Seq[ModifierId])(implicit encoder: BytesEncoder): String = {
    idsToString(ids.map(id => (modifierType, id)))
  }

  def idsToString(invData: InvData)(implicit encoder: BytesEncoder): String = idsToString(invData._1, invData._2)

}
