class CreateLeccionKanjis < ActiveRecord::Migration[5.2]
  def change
    create_table :leccion_kanjis do |t|
      t.string :leccion
      t.string :numero
      t.string :significado
      t.string :imagen_kanji
      t.string :imagen_trazos
      t.string :explicacion
      t.string :extra
      t.references :kanji, foreign_key: true

      t.timestamps
    end
  end
end
