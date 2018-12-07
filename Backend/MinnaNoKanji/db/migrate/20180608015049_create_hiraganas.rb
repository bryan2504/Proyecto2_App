class CreateHiraganas < ActiveRecord::Migration[5.2]
  def change
    create_table :hiraganas do |t|
      t.string :leccion
      t.string :explicacion

      t.timestamps
    end
  end
end
