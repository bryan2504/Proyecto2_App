class CreateLeccionHiraganas < ActiveRecord::Migration[5.2]
  def change
    create_table :leccion_hiraganas do |t|
      t.string :leccion
      t.string :significado
      t.string :url_imagen
      t.references :hiragana, foreign_key: true

      t.timestamps
    end
  end
end
