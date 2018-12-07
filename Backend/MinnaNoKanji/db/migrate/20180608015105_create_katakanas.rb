class CreateKatakanas < ActiveRecord::Migration[5.2]
  def change
    create_table :katakanas do |t|
      t.string :leccion
      t.string :explicacion

      t.timestamps
    end
  end
end
