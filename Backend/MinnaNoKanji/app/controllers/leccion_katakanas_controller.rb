class LeccionKatakanasController < ApplicationController
  before_action :set_leccion_katakana, only: [:show, :edit, :update, :destroy]

  # GET /leccion_katakanas
  # GET /leccion_katakanas.json
  def index
    @leccion_katakanas = LeccionKatakana.all
  end

  # GET /leccion_katakanas/1
  # GET /leccion_katakanas/1.json
  def show
  end

  # GET /leccion_katakanas/new
  def new
    @leccion_katakana = LeccionKatakana.new
  end

  # GET /leccion_katakanas/1/edit
  def edit
  end

  # POST /leccion_katakanas
  # POST /leccion_katakanas.json
  def create
    @leccion_katakana = LeccionKatakana.new(leccion_katakana_params)

    respond_to do |format|
      if @leccion_katakana.save
        format.html { redirect_to @leccion_katakana, notice: 'Leccion katakana was successfully created.' }
        format.json { render :show, status: :created, location: @leccion_katakana }
      else
        format.html { render :new }
        format.json { render json: @leccion_katakana.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /leccion_katakanas/1
  # PATCH/PUT /leccion_katakanas/1.json
  def update
    respond_to do |format|
      if @leccion_katakana.update(leccion_katakana_params)
        format.html { redirect_to @leccion_katakana, notice: 'Leccion katakana was successfully updated.' }
        format.json { render :show, status: :ok, location: @leccion_katakana }
      else
        format.html { render :edit }
        format.json { render json: @leccion_katakana.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /leccion_katakanas/1
  # DELETE /leccion_katakanas/1.json
  def destroy
    @leccion_katakana.destroy
    respond_to do |format|
      format.html { redirect_to leccion_katakanas_url, notice: 'Leccion katakana was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_leccion_katakana
      @leccion_katakana = LeccionKatakana.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def leccion_katakana_params
      params.require(:leccion_katakana).permit(:leccion, :significado, :url_imagen, :katakana_id)
    end
end
