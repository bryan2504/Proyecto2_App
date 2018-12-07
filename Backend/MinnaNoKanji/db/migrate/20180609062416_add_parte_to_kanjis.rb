class AddParteToKanjis < ActiveRecord::Migration[5.2]
  def change
    add_column :kanjis, :parte, :string
  end
end
