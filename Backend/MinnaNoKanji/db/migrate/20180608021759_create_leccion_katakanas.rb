class CreateLeccionKatakanas < ActiveRecord::Migration[5.2]
  def change
    create_table :leccion_katakanas do |t|
      t.string :leccion
      t.string :significado
      t.string :url_imagen
      t.references :katakana, foreign_key: true

      t.timestamps
    end
  end
end
