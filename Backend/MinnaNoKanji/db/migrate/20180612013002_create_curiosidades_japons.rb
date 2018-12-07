class CreateCuriosidadesJapons < ActiveRecord::Migration[5.2]
  def change
    create_table :curiosidades_japons do |t|
      t.string :tipo
      t.string :explicacion
      t.string :url_imagen

      t.timestamps
    end
  end
end
